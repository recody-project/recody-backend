package com.recody.recodybackend.catalog.features.search.movies;

import com.recody.recodybackend.common.contents.movie.Movie;

import java.util.List;

public interface CatalogSearchMoviesHandler {
    
    List<Movie> handle(SearchMovies command);

}
