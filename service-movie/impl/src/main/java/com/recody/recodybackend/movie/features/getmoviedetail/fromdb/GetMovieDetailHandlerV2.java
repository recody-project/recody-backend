package com.recody.recodybackend.movie.features.getmoviedetail.fromdb;

public interface GetMovieDetailHandlerV2<R> {
    
    R handle(GetMovieDetailV2 query);
    
}
