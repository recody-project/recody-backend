package com.recody.recodybackend.catalog.features.getdetail.movie;

import com.recody.recodybackend.movie.Movie;

public interface FetchMovieDetailHandler {
    
    Movie handle(FetchMovieDetail command);

}
