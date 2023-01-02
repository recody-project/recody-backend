package com.recody.recodybackend.catalog.features.fetchmoviedetailv2;

public interface FetchMovieDetailHandlerV2<R> {
    
    R handle(FetchMovieDetailV2 query);

}
