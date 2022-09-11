package com.recody.recodybackend.movie.features.getmoviedetail;

/*
* 영화 정보를 받아오는 핸들러 */
public interface GetMovieDetailHandler {
    
    GetMovieDetailResult handle(GetMovieDetail command);
}
