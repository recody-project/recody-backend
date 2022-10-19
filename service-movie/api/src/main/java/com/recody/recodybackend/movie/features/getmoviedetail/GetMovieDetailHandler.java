package com.recody.recodybackend.movie.features.getmoviedetail;

import com.recody.recodybackend.movie.MovieDetail;

import java.util.concurrent.CompletableFuture;

/*
 * 영화 정보를 받아오는 핸들러 */
public interface GetMovieDetailHandler {
    
    /**
     * 영화 정보를 API 에서 가져온다.
     * 기본적인 구현체는 TMDB API 를 사용하도록 한다.
     */
    MovieDetail handle(GetMovieDetail command);
    
    CompletableFuture<MovieDetail> handleAsync(GetMovieDetail command);
    
    /*
     * 영화 정보를 API 에서 가져온다.
     * 이 메서드는 어떤 출처에서 가져올 지 지정할 수 있다.
     * 현재는 구현하지 않는다.
     */
//    GetMovieDetailResult handle(GetMovieDetail command, MovieSource source);
    
    /**
     * 영화 정보를 DB 에서 가져온다.
     * 이 메서드는 영화 정보가 이미 저장되어 있음을 가정한다.
     * TODO: 추후 handle 메서드로 통합하고 정보를 가져올 출처를 자동적으로 정할 수 있도록 구현한다.
     */
    GetMovieDetailResult handleFromDB(GetMovieDetail command);
}
