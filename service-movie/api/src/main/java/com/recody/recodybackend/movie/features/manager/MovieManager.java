package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.Movie;


public interface MovieManager {
    
    /**
     * 받아온 영화 정보를 저장한다.
     * 영화의 제목, 장르, 국가 등의 정보를 체크하고 영화 엔티티와 매핑하여 저장한다.
     * @param movie 영화 정보
     * @return 저장된 영화 정보의 고유 id
     */
    String register(Movie movie);
}
