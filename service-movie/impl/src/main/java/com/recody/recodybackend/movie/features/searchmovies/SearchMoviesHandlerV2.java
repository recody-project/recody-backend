package com.recody.recodybackend.movie.features.searchmovies;

public interface SearchMoviesHandlerV2<R> {
    
    R handle(SearchMovies query);
    
}
