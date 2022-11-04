package com.recody.recodybackend.catalog.features.search.movies;

import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.web.TMDBSearchedMovie;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ReactiveSearchMoviesHandler {
    
    Mono<List<Movie>> handle(SearchMovies command);
    Mono<List<TMDBSearchedMovie>> handleTmdb(SearchMovies command);
    
}
