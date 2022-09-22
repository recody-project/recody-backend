package com.recody.recodybackend.movie.features.getmoviedetail;

import com.recody.recodybackend.common.openapi.APIFeature;

public interface GetMovieDetail extends APIFeature{
    
    String getMovieId();
    String getLanguage();
}
