package com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.fromdb;

/**
 * 영화의 상세정보를 TMDB ID 를 사용해 DB에서 가져옵니다.
 * @param <R> 반환 타입
 * @author motive
 */
public interface GetMovieDetailWithTMDBIdHandler<R> {
    R handle(GetMovieDetailWithTMDBId command);
}
