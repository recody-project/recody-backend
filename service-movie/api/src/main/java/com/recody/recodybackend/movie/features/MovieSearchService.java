package com.recody.recodybackend.movie.features;

import com.recody.recodybackend.movie.features.searchmovies.SearchMovies;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesByQueryResult;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesResult;

public interface MovieSearchService {
    
    SearchMoviesResult searchMovies(SearchMovies command);
    
    SearchMoviesByQueryResult searchMoviesByQuery(SearchMovies command);
    
}
