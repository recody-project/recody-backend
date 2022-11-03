package com.recody.recodybackend.catalog.features.search.movies;

import com.recody.recodybackend.movie.Movie;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ReactiveSearchMoviesHandler {
    
    Mono<List<Movie>> handle(SearchMovies command);
    
}
