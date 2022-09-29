package com.recody.recodybackend.catalog.features.search.movies;

import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesResult;

public interface SearchMoviesHandler {
    
    SearchMoviesResult handle(SearchMovies command);

}
