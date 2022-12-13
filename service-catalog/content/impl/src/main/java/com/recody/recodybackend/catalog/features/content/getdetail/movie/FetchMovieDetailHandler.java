package com.recody.recodybackend.catalog.features.content.getdetail.movie;

import com.recody.recodybackend.movie.MovieDetailViewModel;

public interface FetchMovieDetailHandler {
    
    MovieDetailViewModel handle(FetchMovieDetail command);

}
