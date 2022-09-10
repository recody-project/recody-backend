package com.recody.recodybackend.movie.features.getmovieDetail;

import com.recody.recodybackend.common.openapi.JsonApiResponse;

/*
* 영화 정보를 받아오는 핸들러 */
public interface GetMovieDetailHandler {
    
    String handleToString(GetMovieDetail request);
    
    GetMovieDetailResponse handleToResponse(GetMovieDetail request);
    
    JsonApiResponse handleToJson(GetMovieDetail request);
}
