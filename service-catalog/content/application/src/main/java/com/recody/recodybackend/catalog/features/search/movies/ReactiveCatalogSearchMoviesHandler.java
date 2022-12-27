package com.recody.recodybackend.catalog.features.search.movies;

import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.Movies;
import com.recody.recodybackend.movie.web.SearchMoviesResult;
import com.recody.recodybackend.movie.web.TMDBSearchedMovie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
class ReactiveCatalogSearchMoviesHandler implements ReactiveSearchMoviesHandler {
    
    private final WebClient movieWebClient;
    private static final String path = "/api/v3/movie/search";
    private static final String pathTmdb = "/api/v1/movie/search";
    private static final String MOVIE_SEARCH_PARAM_NAME = "movieName";
    private static final String LANGUAGE_PARAM_NAME = "language";
    
    
    public ReactiveCatalogSearchMoviesHandler(
            @Qualifier("MovieServiceWebClient") WebClient movieWebClient) {
        this.movieWebClient = movieWebClient;
    }
    
    // TODO Movies 반환
    @Override
    public Mono<List<Movie>> handle(SearchMovies command) {
        log.debug( "handling command: {}", command );
        return movieWebClient.get()
                             .uri( uriBuilder -> uriBuilder.path( path )
                                                 .queryParam( MOVIE_SEARCH_PARAM_NAME, command.getKeyword() )
                                                 .queryParam( LANGUAGE_PARAM_NAME, command.getLanguage() )
                                                 .build() )
                             .retrieve()
                             .bodyToMono( Movies.class )
                             .map( Movies::getMovies );
    }
    
    @Override
    public Mono<List<TMDBSearchedMovie>> handleTmdb(SearchMovies command) {
        log.debug( "handling command: {}", command );
        
        return movieWebClient.get()
                             .uri( uriBuilder -> uriBuilder.path( pathTmdb )
                                                           .queryParam( MOVIE_SEARCH_PARAM_NAME, command.getKeyword() )
                                                           .queryParam( LANGUAGE_PARAM_NAME, command.getLanguage() )
                                                           .build() )
                             .retrieve()
                             .bodyToMono( SearchMoviesResult.class )
                             .map( SearchMoviesResult::getMovies );
    }
}
