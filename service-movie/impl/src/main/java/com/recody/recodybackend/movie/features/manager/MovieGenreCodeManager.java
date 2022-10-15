package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.data.genre.MovieGenreCodeEntity;
import com.recody.recodybackend.movie.general.MovieGenre;

public interface MovieGenreCodeManager {
    
    /**
     * 장르가 없으면 저장하고 결과를 반환한다.
     * 저장된 entity 는 영화에 해당하는 장르와 매핑되는 MovieGenreEntity 를 저장하는 데에 사용할 수 있다.
     * @param genre 영화 장르 정보
     * @return 저장한 장르의 entity
     */
    MovieGenreCodeEntity register(MovieGenre genre);
    
}
