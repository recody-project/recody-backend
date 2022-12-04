package com.recody.recodybackend.movie.features;

import com.recody.recodybackend.common.data.QueryMetadata;
import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.MovieDetail;
import com.recody.recodybackend.movie.MovieDetailViewModel;
import com.recody.recodybackend.movie.Movies;
import com.recody.recodybackend.movie.data.movie.MovieDetailMapper;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieMapper;
import com.recody.recodybackend.movie.features.applicationevent.MoviesSearched;
import com.recody.recodybackend.movie.features.getmoviedetail.fromapi.FetchedMovieDetailViewModel;
import com.recody.recodybackend.movie.features.getmoviedetail.fromapi.GetMovieDetailFromTMDBHandler;
import com.recody.recodybackend.movie.features.getmoviedetail.fromapi.TMDBFetchedMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.fromdb.GetMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.fromdb.GetMovieDetailHandler;
import com.recody.recodybackend.movie.features.manager.MovieManager;
import com.recody.recodybackend.movie.features.searchmovies.SearchMovies;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesHandler;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;
import com.recody.recodybackend.movie.web.SearchMoviesByQueryResult;
import com.recody.recodybackend.movie.web.SearchMoviesResult;
import com.recody.recodybackend.movie.web.TMDBSearchedMovie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
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
class DefaultMovieService implements MovieSearchService,
                                     MovieDetailService<FetchedMovieDetailViewModel, GetMovieDetail> {
    
    public static final int MINIMUM_SEARCH_RESULT_SIZE = 10;
    private final GetMovieDetailHandler getMovieDetailHandler;
    private final SearchMoviesHandler<TMDBMovieSearchNode> tMDBSearchMoviesHandler;
    private final SearchMoviesHandler<MovieEntity> dbSearchMoviesHandler;
    private final MovieMapper movieMapper;
    private final MovieDetailMapper movieDetailMapper;
    private final MovieManager movieManager;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final GetMovieDetailFromTMDBHandler getMovieDetailFromTMDBHandler;
    
    /**
     * TMDB 에서 영화의 상세정보를 가져와 반환한다.
     * 받아온 결과를 매핑하여 그대로 반환하며, 저장은 비동기로 이루어진다.
     */
    @Override
    public FetchedMovieDetailViewModel fetchMovieDetail(GetMovieDetail args) {
        TMDBFetchedMovieDetail tmdbFetchedMovieDetail = getMovieDetailFromTMDBHandler.handle( args );
        return movieDetailMapper.toViewModel( tmdbFetchedMovieDetail );
        
    }
    
    
    /**
     * DB 에서 영화정보를 가져온다.
     * 상세 정보는 MovieDetail 객체를 포함한다.
     * 주의: @Transactional 을 붙이지 말것.
     */
    @Override
    public MovieDetailViewModel getMovieDetail(GetMovieDetail command) {
        MovieDetail movieDetail = getMovieDetailHandler.handleFromDB( command );
        return movieDetailMapper.toViewModel( movieDetail );
    }
    
    @Override
    @Transactional
    public SearchMoviesResult searchMovies(SearchMovies command) {
        String movieName = command.getMovieName();
        Locale locale = Locale.forLanguageTag( command.getLanguage() );
        Page<MovieEntity> moviesFromDB = dbSearchMoviesHandler.handlePage( command );
        // db 에 결과가 있는 경우에는 그 결과를 반환한다.
        if ( moviesFromDB.getSize() > MINIMUM_SEARCH_RESULT_SIZE ) {
            List<TMDBSearchedMovie> tmdbSearchedMovies = movieMapper.toTMDBMovie( moviesFromDB.getContent(),
                                                                                  locale );
            return SearchMoviesResult.builder()
                                     .metadata( new QueryMetadata( moviesFromDB, true ) )
                                     .movies( tmdbSearchedMovies )
                                     .build();
        }
        
        // API 에서 가져옴.
        Page<TMDBMovieSearchNode> tmdbMoviesPage = tMDBSearchMoviesHandler.handlePage( command );
        
        applicationEventPublisher.publishEvent(
                MoviesSearched.builder().tmdbMovies( tmdbMoviesPage.getContent() ).locale( locale ).build() );
        
        List<TMDBSearchedMovie> movies = movieMapper.toTMDBMovie( tmdbMoviesPage.getContent() );
        
        return SearchMoviesResult.builder()
                                 .metadata( new QueryMetadata( tmdbMoviesPage, false ) )
                                 .movies( movies )
                                 .build();
    }
    
    @Override
    @Transactional
    public Movies searchMoviesMix(SearchMovies command) {
        String movieName = command.getMovieName();
        Locale locale = Locale.forLanguageTag( command.getLanguage() );
        List<MovieEntity> moviesFromDB = dbSearchMoviesHandler.handle( command );
        // db 에 결과가 있는 경우에는 그 결과를 반환한다.
        if ( moviesFromDB.size() > MINIMUM_SEARCH_RESULT_SIZE ) {
            List<Movie> movies = movieMapper.toMovie( moviesFromDB, locale );
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
        String language = command.getLanguage();
        Locale locale = Locale.forLanguageTag( language );
        Page<MovieEntity> movieEntitiesPage = dbSearchMoviesHandler.handlePage( command );
        List<Movie> movies = movieMapper.toMovie( movieEntitiesPage.getContent(), locale );
        log.debug( "Searched movies: {}", movieEntitiesPage.getTotalElements() );
        return SearchMoviesByQueryResult.builder()
                                        .metadata( new QueryMetadata( movieEntitiesPage, true ) )
                                        .movies( movies )
                                        .build();
    }
    
    @Override
    public Movies searchMoviesByQueryData(SearchMovies command) {
        Locale locale = Locale.forLanguageTag( command.getLanguage() );
        List<MovieEntity> movieEntities = dbSearchMoviesHandler.handle( command );
        List<Movie> movies = movieMapper.toMovie( movieEntities, locale );
        return new Movies( movies );
    }
}
