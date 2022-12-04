package com.recody.recodybackend.movie.features;

import com.recody.recodybackend.movie.MovieDetailViewModel;

/**
 * @param <A> 사용할 API 에서 요구하는 요소들
 * @param <R> 반환 타입
 * @author motive
 */
public interface MovieDetailService<R, A> {
    
    R fetchMovieDetail(A args);
    
    MovieDetailViewModel getMovieDetail(A command);
    
    
}
