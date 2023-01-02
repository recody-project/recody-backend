package com.recody.recodybackend.catalog.features.getmoviedetail;

public interface GetMovieDetailHandler<R> {
    
    R handle(GetMovieDetail command);

}
