package com.recody.recodybackend.movie.features.searchmovies;

public interface SearchMoviesHandler<R> {
    
    R handle(SearchMovies query);
    
}
