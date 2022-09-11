package com.recody.recodybackend.movie.features.getmoviedetail;

import com.recody.recodybackend.common.openapi.Request;

public interface GetMovieDetail extends Request{
    
    String getMovieId();
    String getLanguage();
}
