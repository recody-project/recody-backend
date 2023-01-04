package com.recody.recodybackend.movie.features.applicationevent;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.movie.MovieInfo;
import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBCast;
import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBCrew;
import com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.manager.MovieManager;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultMovieEventListener {
    
    private final MovieManager movieManager;
    
    @Async( Recody.MOVIE_TASK_EXECUTOR )
    @EventListener
    @Transactional
    public void on(MovieDetailFetched event) {
        log.debug( "consuming event: {}", event );
        TMDBMovieDetail detail = event.getTmdbMovieDetail();
        Locale locale = event.getLocale();
        List<TMDBCast> cast = event.getCasts();
        List<TMDBCrew> crew = event.getCrews();
        
        CompletableFuture<MovieInfo> registeredMovieFuture = movieManager.registerAsync( detail, locale );
        
        registeredMovieFuture
                .whenComplete((movieInfo1, throwable) ->
                                      movieManager.actor()
                                                  .registerAsync( movieInfo1, cast, Locale.ENGLISH )
                                                  .thenAccept(them -> log.info("영화에 배우 정보를 등록하였습니다.: {}", them.size())))
                .whenComplete((movieInfo1, throwable) ->
                                      movieManager.director()
                                                  .registerAsync(movieInfo1, crew, Locale.ENGLISH)
                                                  .thenAccept(them -> log.info("영화에 감독 정보를 등록하였습니다.: {}", them.size()))
                             );
    }
    
    @Async( Recody.MOVIE_TASK_EXECUTOR )
    @EventListener
    @Transactional
    public void on(SearchingMoviesFetched event) {
        log.debug( "consuming event: {}", event );
        List<TMDBMovieSearchNode> tmdbMovies = event.getTmdbMovies();
        Locale locale = event.getLocale();
        
        movieManager.movie()
                    .registerAsync( tmdbMovies, locale )
                    .thenAccept( them -> log.info( "registered movies: {}", them.size() ) );
        
    }
}
