package com.recody.recodybackend.catalog.features.search.movies;

public interface CatalogSearchMoviesHandler<R> {
    
   R handle(SearchMovies command);

}
