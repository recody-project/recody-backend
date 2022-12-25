package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.movie.Actor;
import com.recody.recodybackend.movie.Director;
import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.MovieInfo;
import com.recody.recodybackend.movie.data.MovieEntityManager;
import com.recody.recodybackend.movie.data.genre.MovieGenreCodeManager;
import com.recody.recodybackend.movie.data.genre.MovieGenreEntity;
import com.recody.recodybackend.movie.data.movie.MovieDetailMapper;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieMapper;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.data.people.*;
import com.recody.recodybackend.movie.events.MovieCreated;
import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBCast;
import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBCrew;
import com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.dto.TMDBMovieGenre;
import com.recody.recodybackend.movie.features.projection.MovieEventPublisher;
import com.recody.recodybackend.movie.features.resolvegenre.MovieGenreResolver;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;
import com.recody.recodybackend.movie.features.tmdb.TMDB;
import com.recody.recodybackend.movie.features.tmdb.TMDBMovieID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultMovieManager implements MovieManager {
    
    private final MovieInfoRegistrar<Director, TMDBCrew> directorRegistrar;
    private final MovieInfoRegistrar<Actor, TMDBCast> actorRegistrar;
    private final MovieInfoRegistrar<MovieInfo, TMDBMovieDetail> infoRegistrar;
    private final MovieRegistrar<TMDBMovieSearchNode> movieRegistrar;
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final MovieDetailMapper movieDetailMapper;
    private final MovieEntityManager movieEntityManager;
    private final MovieGenreCodeManager genreCodeManager;
    private final MovieEventPublisher movieEventPublisher;
    
    @Override
    @Transactional
    public MovieInfo register(TMDBMovieDetail source, Locale locale) {
        log.debug( "registering TMDBMovieDetail" );
        
        Optional<MovieEntity> optionalMovieEntity = movieRepository.findByTmdbId( source.getId() );
        if ( optionalMovieEntity.isPresent() ) {
            // 있으면 업데이트 후 반환한다.
            MovieEntity movieEntity = optionalMovieEntity.get();
            MovieEntity updatedEntity = movieDetailMapper.update( movieEntity, source, locale );
            MovieInfo movie = movieMapper.toMovieInfo( updatedEntity, locale );
            log.info( "영화를 업데이트 후 반환합니다. movie: {}", movie );
            return movie;
        }
        
        List<TMDBMovieGenre> genres = source.getGenres();
        
        MovieEntity movieEntity = movieDetailMapper.newEntity( source, locale );
        log.debug( "new movieEntity: {}", movieEntity );
        
        MovieEntity savedEntity = movieRepository.save( movieEntity );
        log.debug( "savedEntity: {}", savedEntity );
        
        publishNewMovieCreated( savedEntity );
        
        genreCodeManager.registerAsync( genres )
                        .thenAccept( genreCodeEntity -> movieEntityManager.saveMovieGenre( savedEntity, genreCodeEntity ) );
        
        MovieInfo movie = movieMapper.toMovieInfo( savedEntity, locale );
        log.info( "영화를 등록하였습니다. movie: {}", movie );
        return movie;
    }
    
    @Override
    @Transactional
    public Optional<Movie> load(TMDBMovieID sourceIdentifier, Locale locale) {
        log.debug( "Loading Movie with tmdbId: {}", sourceIdentifier );
        Integer id = sourceIdentifier.getId();
        MovieEntity movieEntity = movieRepository.findByTmdbId( id )
                                                 .orElse( null );
        Movie movie = movieMapper.toMovie( movieEntity, locale );
        log.info( "Loaded Movie: {}", movie );
        return Optional.ofNullable( movie );
    }
    
    @Override
    public MovieRegistrar<TMDBMovieSearchNode> movie() {
        return movieRegistrar;
    }
    
    @Override
    public MovieInfoRegistrar<MovieInfo, TMDBMovieDetail> info() {
        return infoRegistrar;
    }
    
    @Override
    public MovieInfoRegistrar<Actor, TMDBCast> actor() {
        return actorRegistrar;
    }
    
    @Override
    public MovieInfoRegistrar<Director, TMDBCrew> director() {
        return directorRegistrar;
    }
    
    private void publishNewMovieCreated(MovieEntity savedEntity) {
        MovieCreated event = MovieCreated.builder()
                                         .contentId( savedEntity.getId() )
                                         .posterUrl( savedEntity.getPosterPath() )
                                         .koreanTitle( savedEntity.getTitle().getKoreanTitle() )
                                         .englishTitle( savedEntity.getTitle().getEnglishTitle() )
                                         .build();
        movieEventPublisher.publish( event );
    }
    
    @Component
    @RequiredArgsConstructor
    @Slf4j
    public static class ConcreteMovieRegistrar implements MovieRegistrar<TMDBMovieSearchNode> {
        
        private final MovieRepository movieRepository;
        private final MovieMapper movieMapper;
        private final MovieGenreCodeManager genreCodeManager;
        private final MovieEntityManager movieEntityManager;
        private final MovieGenreResolver genreResolver;
        
        private final MovieEventPublisher movieEventPublisher;
        
        
        @Override
        public Movie register(TMDBMovieSearchNode source, Locale locale) {
            log.debug( "registering TMDBMovieSearchNode, locale: {}", locale );
            Optional<MovieEntity> optionalMovie;
            String title = source.getTitle();
            optionalMovie = movieRepository.findByTmdbId( source.getId() );
            
            if ( optionalMovie.isPresent() ) {
                MovieEntity currentMovieEntity = optionalMovie.get();
                MovieEntity updatedMovieEntity = movieEntityManager.upsertTitleByLocale( currentMovieEntity, title, locale );
                log.debug( "이미 존재하는 영화 정보를 업데이트 후 반환합니다." );
                return movieMapper.toMovie( updatedMovieEntity, locale );
            }
            
            List<Integer> genreIds = source.getGenreIds();
            
            List<TMDBMovieGenre> movieGenres = genreResolver.toTMDBGenres( genreIds );
            
            MovieEntity movieEntity = movieMapper.newEntity( source, locale );
            log.debug( "new MovieEntity: {} ", movieEntity );
            
            MovieEntity savedMovieEntity = movieRepository.saveAndFlush( movieEntity );
            log.debug( "savedMovieEntity id: {}", savedMovieEntity.getId() );
            
            publishNewMovieCreated( savedMovieEntity );
            
            genreCodeManager.registerAsync( movieGenres )
                            .thenAccept( genres -> {
                                movieEntityManager.saveMovieGenre( savedMovieEntity, genres );
                                log.info( "영화의 장르들을 저장하였습니다.: {}", genres.size() );
                            } );
            
            log.debug( "검색한 영화를 저장하였습니다.: {}", savedMovieEntity.getId() );
            return movieMapper.toMovie( savedMovieEntity, locale );
        }
        
        private void publishNewMovieCreated(MovieEntity savedEntity) {
            MovieCreated event = MovieCreated.builder()
                                             .contentId( savedEntity.getId() )
                                             .posterUrl( savedEntity.getPosterPath() )
                                             .koreanTitle( savedEntity.getTitle().getKoreanTitle() )
                                             .englishTitle( savedEntity.getTitle().getEnglishTitle() )
                                             .build();
            movieEventPublisher.publish( event );
        }
        
        @Override
        public List<Movie> register(List<TMDBMovieSearchNode> sources, Locale locale) {
            log.info( " {} 개의 영화를 저장합니다. ", sources.size() );
            return sources.stream().map( source -> this.register( source, locale ) ).collect( Collectors.toList() );
        }
    }
    
    
    @Component
    @RequiredArgsConstructor
    @Slf4j
    public static class ConcreteInfoRegistrar implements MovieInfoRegistrar<MovieInfo, TMDBMovieDetail> {
        
        private final MovieRepository movieRepository;
        private final MovieDetailMapper movieDetailMapper;
        private final MovieMapper movieMapper;
        
        @Override
        @Transactional
        public MovieInfo register(Movie movie, TMDBMovieDetail source, Locale locale) {
            log.debug( "registering TMDBMovieDetail for movie: {}", movie );
            Optional<MovieEntity> optionalMovie = movieRepository.findById( movie.getContentId() );
            
            if ( optionalMovie.isEmpty() ) {
                throw new ContentNotFoundException();
            }
            
            // 저장된 영화이다. 상세 정보 중에 업데이트할 정보를 찾아 업데이트한다.
            MovieEntity entity = optionalMovie.get();
            // production country, spoken language
            MovieEntity updatedEntity = movieDetailMapper.update( entity, source, locale );
            List<MovieGenreEntity> genres = updatedEntity.getGenres();
            log.debug( "genres: {}", genres );
            MovieInfo movieInfo = movieMapper.toMovieInfo( updatedEntity, locale );
            
            log.info( "TMDBMovieDetail 로 영화정보를 저장하였습니다.: {}", movieInfo );
            return movieInfo;
        }
        
        @Override
        public List<MovieInfo> register(Movie content, List<TMDBMovieDetail> tmdbMovieDetails, Locale locale) {
            log.info( "{} 개의 영화를 저장하거나 업데이트합니다.", tmdbMovieDetails.size() );
            return MovieInfoRegistrar.super.register( content, tmdbMovieDetails, locale );
        }
    }
    
    
    @Component
    @RequiredArgsConstructor
    @Slf4j
    public static class ActorRegistrar implements MovieInfoRegistrar<Actor, TMDBCast> {
        
        private final MoviePersonMapper moviePersonMapper;
        private final MoviePersonRepository personRepository;
        private final MovieEntityManager movieEntityManager;
        private final MovieRepository movieRepository;
        
        @Override
        public Actor register(Movie movie, TMDBCast source, Locale locale) {
            log.debug( "registering actor for movie: {}", movie );
            MovieEntity movieEntity = movieRepository.findById( movie.getContentId() )
                                                     .orElseThrow( ContentNotFoundException::new );
            
            Integer tmdbId = source.getId();
            Optional<MoviePersonEntity> optionalPerson = personRepository.findByTmdbId( tmdbId );
            if ( optionalPerson.isPresent() ) {
                MoviePersonEntity entity = optionalPerson.get();
                //TODO Actor 있을 시 업데이트
                MovieActorEntity savedActorEntity = movieEntityManager.saveActor( movieEntity, entity, source );
                Actor actor = moviePersonMapper.toActor( savedActorEntity, locale );
                log.debug( "updated actor: {}", actor );
                return actor;
            }
            
            MoviePersonEntity before = moviePersonMapper.newPersonEntity( source );
            MoviePersonEntity savedPersonEntity = personRepository.save( before );
            MovieActorEntity actorEntity = movieEntityManager.saveActor( movieEntity, savedPersonEntity, source );
            Actor actor = moviePersonMapper.toActor( actorEntity, locale);
            log.debug( "saved new actor: {}", actor );
            return actor;
        }
        
        @Override
        public List<Actor> register(Movie movie, List<TMDBCast> source, Locale locale) {
            return source.stream()
                         .filter( cast -> cast.getKnownForDepartment().equals( TMDB.ACTING ) )
                         .limit( TMDB.ACTOR_MAX_SIZE )
                         .map( cast -> this.register( movie, cast, locale ) )
                         .collect( Collectors.toList() );
        }
    }
    
    
    @Component
    @RequiredArgsConstructor
    @Slf4j
    public static class DirectorRegistrar implements MovieInfoRegistrar<Director, TMDBCrew> {
        
        private final MoviePersonMapper moviePersonMapper;
        private final MoviePersonRepository personRepository;
        private final MovieEntityManager movieEntityManager;
        private final MovieRepository movieRepository;
        
        
        @Override
        public Director register(Movie movie, TMDBCrew crew, Locale locale) {
            log.debug( "registering director for movie: {}", movie );
            MovieEntity movieEntity = movieRepository.findById( movie.getContentId() )
                                                     .orElseThrow( ContentNotFoundException::new );
            
            Integer tmdbId = crew.getId();
            Optional<MoviePersonEntity> optionalPerson = personRepository.findByTmdbId( tmdbId );
            if ( optionalPerson.isPresent() ) {
                //TODO Director 있을 시 업데이트
                MoviePersonEntity entity = optionalPerson.get();
                MovieDirectorEntity savedDirectorEntity = movieEntityManager.saveDirector( movieEntity, entity );
                Director director = moviePersonMapper.toDirector( savedDirectorEntity, locale );
                log.debug( "updated director: {}", director );
                
                return director;
            }
            
            MoviePersonEntity before = moviePersonMapper.newPersonEntity( crew );
            MoviePersonEntity savedPersonEntity = personRepository.save( before );
            MovieDirectorEntity movieDirectorEntity = movieEntityManager.saveDirector( movieEntity, savedPersonEntity );
            Director director = moviePersonMapper.toDirector( movieDirectorEntity, locale );
            log.debug( "saved new director: {}", director );
            return director;
            
        }
        
        @Override
        public List<Director> register(Movie movie, List<TMDBCrew> crew, Locale locale) {
            List<TMDBCrew> fewCrews = crew.stream()
                                          .filter( tmdbCast -> tmdbCast.getJob().equals( TMDB.DIRECTOR ) )
                                          .limit( TMDB.DIRECTOR_MAX_SIZE )
                                          .collect( Collectors.toList() );
            ArrayList<Director> directors = new ArrayList<>();
            for (TMDBCrew tmdbCrew : fewCrews) {
                Director registered = this.register( movie, tmdbCrew, locale );
                directors.add( registered );
            }
            return directors;
        }
    }
}
