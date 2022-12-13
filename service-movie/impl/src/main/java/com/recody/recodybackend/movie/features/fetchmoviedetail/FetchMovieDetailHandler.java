package com.recody.recodybackend.movie.features.fetchmoviedetail;

import com.recody.recodybackend.common.CommandHandler;

/**
 * 외부 API 에서 영화 정보를 가져온다.
 * @param <R> 결과 값 타입
 * @param <A> 커맨드 타입
 * @author motive
 */
public interface FetchMovieDetailHandler<R, A> extends CommandHandler<R, A> {
    
    R handle(A args);
}
