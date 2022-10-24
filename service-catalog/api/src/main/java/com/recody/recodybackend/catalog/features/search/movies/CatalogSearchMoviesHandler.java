package com.recody.recodybackend.catalog.features.search.movies;

import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesByQueryResult;

public interface CatalogSearchMoviesHandler {
    
    SearchMoviesByQueryResult handle(SearchMovies command);

}
