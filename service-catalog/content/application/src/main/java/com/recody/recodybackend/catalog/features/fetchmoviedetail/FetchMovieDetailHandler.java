package com.recody.recodybackend.catalog.features.fetchmoviedetail;

import com.recody.recodybackend.movie.MovieDetailViewModel;

public interface FetchMovieDetailHandler {
    
    MovieDetailViewModel handle(FetchMovieDetail command);

}
