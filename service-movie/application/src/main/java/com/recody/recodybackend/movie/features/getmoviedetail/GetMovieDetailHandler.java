package com.recody.recodybackend.movie.features.getmoviedetail;

/**
 * Movie Id 를 사용해 영화의 상세 내용을 쿼리합니다.
 * @param <R> 반환 타입
 * @author motive
 */
public interface GetMovieDetailHandler<R> {
    
    R handle(GetMovieDetail query);
    
}
