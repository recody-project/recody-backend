package com.recody.recodybackend.movie.features.getmoviedetail.fromdb;

import com.recody.recodybackend.movie.MovieDetail;

/**
 * 영화의 상세정보를 가져온다.
 * API 에서 가져온 정보일 수도 있고, DB 에서 가져온 정보일 수도 있다.
 * @author motive
 */
public interface GetMovieDetailHandler {
    
    /**
     * 영화 정보를 API 에서 가져온다.
     * 기본적인 구현체는 TMDB API 를 사용하도록 한다.
     */
    MovieDetail handle(GetMovieDetail command);
    
    /**
     * 영화 정보를 DB 에서 가져온다.
     * 이 메서드는 영화 정보가 이미 저장되어 있음을 가정한다.
     */
    MovieDetail handleFromDB(GetMovieDetail command);
}
