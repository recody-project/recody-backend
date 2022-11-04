package com.recody.recodybackend.movie.features;

import com.recody.recodybackend.movie.*;
import com.recody.recodybackend.movie.data.movie.MovieDetailMapper;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieMapper;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.data.people.MoviePersonMapper;
import com.recody.recodybackend.movie.features.applicationevent.MovieDetailFetched;
import com.recody.recodybackend.movie.features.applicationevent.MoviesSearched;
import com.recody.recodybackend.movie.features.getmoviecredit.FetchMovieCreditsHandler;
import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBCast;
import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBCrew;
import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBGetMovieCreditResponse;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.fromapi.FetchMovieDetailHandler;
import com.recody.recodybackend.movie.features.getmoviedetail.fromapi.TMDBFetchedMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.fromdb.GetMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.fromdb.GetMovieDetailHandler;
import com.recody.recodybackend.movie.features.getmoviedetail.fromdb.GetMovieDetailResult;
import com.recody.recodybackend.movie.features.manager.MovieManager;
import com.recody.recodybackend.movie.features.searchmovies.SearchMovies;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesByQueryResult;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesHandler;
import com.recody.recodybackend.movie.web.SearchMoviesResult;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;
import com.recody.recodybackend.movie.features.tmdb.TMDBMovieID;
import com.recody.recodybackend.movie.web.TMDBSearchedMovie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

/**
 * @author motive
 */
@Component
@RequiredArgsConstructor
@Slf4j
class DefaultMovieService implements MovieSearchService, MovieDetailService<TMDBFetchedMovieDetail, GetMovieDetail> {
    
    public static final int MINIMUM_SEARCH_RESULT_SIZE = 10;
    private final GetMovieDetailHandler getMovieDetailHandler;
    private final SearchMoviesHandler<TMDBMovieSearchNode> tMDBSearchMoviesHandler;
    private final MovieMapper movieMapper;
    private final MovieManager movieManager;
    private final MovieDetailMapper movieDetailMapper;
    private final MovieRepository movieRepository;
    private final MoviePersonMapper personMapper;
    private final FetchMovieDetailHandler<TMDBMovieDetail, GetMovieDetail> fetchMovieDetailHandler;
    private final FetchMovieCreditsHandler<TMDBGetMovieCreditResponse, TMDBMovieID> fetchMovieCreditsHandler;
    private final ApplicationEventPublisher applicationEventPublisher;
    
    // TODO 정말 필요한지 체크
    
    /**
     * TMDB 에서 영화의 상세정보를 가져와 반환한다.
     * 받아온 결과를 매핑하여 그대로 반환하며, 저장은 비동기로 이루어진다.
     */
    @Override
    public TMDBFetchedMovieDetail fetchMovieDetail(GetMovieDetail args) {
        
        Integer tmdbId = args.getTmdbId();
        Locale locale = Locale.forLanguageTag( args.getLanguage() );
        TMDBMovieID tmdbMovieID = TMDBMovieID.of( tmdbId );
        
        TMDBMovieDetail detail =
                fetchMovieDetailHandler.handleAsync( args ).join();
        
        TMDBGetMovieCreditResponse creditResponse =
                fetchMovieCreditsHandler.handleAsync( tmdbMovieID ).join();
        
        List<TMDBCast> cast = creditResponse.getCast();
        List<TMDBCrew> crew = creditResponse.getCrew();
        
        applicationEventPublisher
                .publishEvent( MovieDetailFetched.builder()
                                                 .tmdbMovieDetail( detail )
                                                 .locale( locale )
                                                 .casts( cast )
                                                 .crews( crew )
                                                 .build() );
        
        TMDBFetchedMovieDetail fetchedMovieDetail = movieDetailMapper.toFetchedMovieDetail( detail );
        
        List<Director> directors = personMapper.toDirector( crew );
        List<Actor> actors = personMapper.toActor( cast );
        
        fetchedMovieDetail.setDirectors( directors );
        fetchedMovieDetail.setActors( actors );
        
        return fetchedMovieDetail;
        
    }
    
    
    /**
     * DB 에서 영화정보를 가져온다.
     * 상세 정보는 MovieDetail 객체를 포함한다.
     * 주의: @Transactional 을 붙이지 말것.
     */
    @Override
    public GetMovieDetailResult getMovieDetail(GetMovieDetail command) {
        MovieDetail movieDetail;
        movieDetail = getMovieDetailHandler.handleFromDB( command );
        if ( movieDetail != null ) {
            List<Actor> actors = movieDetail.getActors();
            List<Director> directors = movieDetail.getDirectors();
            if ( !(actors.isEmpty() || directors.isEmpty()) ) {
                return GetMovieDetailResult.builder().detail( movieDetail ).requestInfo( command ).build();
            }
        }
        else {
            log.warn( "DB 에서 영화를 찾지 못했음. API 요청 시도 command: {}", command );
        }
        
        movieDetail = getMovieDetailHandler.handle( command );
        log.info( "MovieDetail 을 가져왔습니다. {}", movieDetail.getTitle() );
        return GetMovieDetailResult.builder().detail( movieDetail ).requestInfo( command ).build();
    }
    
    @Override
    @Transactional
    public SearchMoviesResult searchMovies(SearchMovies command) {
        String movieName = command.getMovieName();
        Locale locale = Locale.forLanguageTag( command.getLanguage() );
        List<MovieEntity> movieEntities = movieRepository.findByTitleLike( movieName, locale );
        
        // db 에 결과가 있는 경우에는 그 결과를 반환한다.
        if ( movieEntities.size() > MINIMUM_SEARCH_RESULT_SIZE ) {
            List<TMDBSearchedMovie> movies = movieMapper.toTMDBMovie( movieEntities, locale );
            return SearchMoviesResult.builder().requestedLanguage( locale ).movies( movies ).total( movies.size() ).build();
        }
        
        // API 에서 가져옴.
        List<TMDBMovieSearchNode> tmdbMovies = tMDBSearchMoviesHandler.handle( command );
        
        applicationEventPublisher.publishEvent( MoviesSearched.builder()
                                                              .tmdbMovies( tmdbMovies )
                                                              .locale( locale )
                                                              .build() );
        
        List<TMDBSearchedMovie> movies = movieMapper.toTMDBMovie( tmdbMovies );
        
        return SearchMoviesResult.builder().requestedLanguage( locale ).movies( movies ).total( movies.size() ).build();
    }
    
    @Override
    @Transactional
    public Movies searchMoviesMix(SearchMovies command) {
        String movieName = command.getMovieName();
        Locale locale = Locale.forLanguageTag( command.getLanguage() );
        List<MovieEntity> movieEntities = movieRepository.findByTitleLike( movieName, locale );
        
        // db 에 결과가 있는 경우에는 그 결과를 반환한다.
        if ( movieEntities.size() > MINIMUM_SEARCH_RESULT_SIZE ) {
            List<Movie> movies = movieMapper.toMovie( movieEntities, locale );
            return new Movies( movies );
        }
        
        // API 에서 가져옴.
        List<TMDBMovieSearchNode> tmdbMovies = tMDBSearchMoviesHandler.handle( command );
        
        CompletableFuture<List<Movie>> moviesFuture = movieManager.movie()
                                                                  .registerAsync( tmdbMovies, locale );
        
        moviesFuture.thenAccept( them -> log.info( "registered movies: {}", them.size() ) );
        
        return new Movies( moviesFuture.join() );
    }
    
    @Override
    @Transactional
    public SearchMoviesByQueryResult searchMoviesByQuery(SearchMovies command) {
        log.debug( "Searching from DB, command: {}", command );
        String movieName = command.getMovieName();
        String language = command.getLanguage();
        Locale locale = Locale.forLanguageTag( language );
        List<MovieEntity> movieEntities = movieRepository.findByTitleLike( movieName, locale );
        List<Movie> movies = movieMapper.toMovie( movieEntities, locale );
        log.debug( "Searched movies: {}", movies.size() );
        return SearchMoviesByQueryResult.builder()
                                        .requestedLanguage( locale )
                                        .movies( movies )
                                        .total( movies.size() )
                                        .build();
    }
    
    @Override
    public Movies searchMoviesByQueryData(SearchMovies command) {
        log.debug( "Searching from DB, command: {}", command );
        String movieName = command.getMovieName();
        String language = command.getLanguage();
        Locale locale = Locale.forLanguageTag( language );
        List<MovieEntity> movieEntities = movieRepository.findByTitleLike( movieName, locale );
        List<Movie> movies = movieMapper.toMovie( movieEntities, locale );
        log.debug( "Searched movies: {}", movies.size() );
        return new Movies( movies );
    }
}
