package com.recody.recodybackend.movie.features;

import com.recody.recodybackend.movie.Movies;
import com.recody.recodybackend.movie.features.searchmovies.SearchMovies;
import com.recody.recodybackend.movie.web.SearchMoviesByQueryResult;
import com.recody.recodybackend.movie.web.SearchMoviesResult;

public interface MovieSearchService {
    
    SearchMoviesResult searchMovies(SearchMovies command);
    
    Movies searchMoviesMix(SearchMovies command);
    
    SearchMoviesByQueryResult searchMoviesByQuery(SearchMovies command);
    
    Movies searchMoviesByQueryData(SearchMovies command);
    
}
