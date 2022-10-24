package com.recody.recodybackend.movie.features;

import com.recody.recodybackend.movie.features.getmoviedetail.fromdb.GetMovieDetailResult;

/**
 * @param <A> 사용할 API 에서 요구하는 요소들
 * @param <R> 반환 타입
 * @author motive
 */
public interface MovieDetailService<R, A> {
    
    R fetchMovieDetail(A args);
    
    GetMovieDetailResult getMovieDetail(A command);
    
    
}