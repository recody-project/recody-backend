package com.recody.recodybackend.catalog.features.getdetail.movie;

import com.recody.recodybackend.movie.MovieDetail;

public interface FetchMovieDetailHandler {
    
    MovieDetail handle(FetchMovieDetail command);

}
