package com.recody.recodybackend.catalog.features.getmoviedetail;

public interface GetMovieDetailFromMovieDBHandler<R> {
    
    R handle(GetMovieDetailFromMovieDB query);
    
}
