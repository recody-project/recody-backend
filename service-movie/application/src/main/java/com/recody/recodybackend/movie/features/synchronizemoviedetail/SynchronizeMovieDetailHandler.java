package com.recody.recodybackend.movie.features.synchronizemoviedetail;

public interface SynchronizeMovieDetailHandler<R> {
    
    R handle(SynchronizeMovieDetail command);
    
}
