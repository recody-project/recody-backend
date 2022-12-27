package com.recody.recodybackend.movie.features.searchmoviesfromtmdb;

public interface SearchMoviesFromTMDBHandler<R> {
    
    R handle(SearchMoviesFromTMDB command);
}
