package com.recody.recodybackend.movie.features;

import com.recody.recodybackend.movie.features.getmoviedetail.GetMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.GetMovieDetailResult;
import com.recody.recodybackend.movie.features.searchmovies.SearchMovies;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesResult;

public interface MovieService {
    
    GetMovieDetailResult getMovieDetail(GetMovieDetail command);
    
    SearchMoviesResult searchMovies(SearchMovies command);
    
}
