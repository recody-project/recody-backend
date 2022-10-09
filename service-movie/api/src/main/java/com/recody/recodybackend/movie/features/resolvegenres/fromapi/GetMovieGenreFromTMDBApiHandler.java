package com.recody.recodybackend.movie.features.resolvegenres.fromapi;

import com.recody.recodybackend.movie.general.MovieGenre;

/*
* TMDB api 에서 장르정보를 찾는다.
* tmdbGenreId -> MovieGenre
* 장르 정보를 여기서 미리 가지고 있는다. */
public interface GetMovieGenreFromTMDBApiHandler {
    
    /*
    * 이 인스턴스가 장르 정보를 가지고 있는지 물어볼 수 있다. */
    boolean hasGenres();
    
    /*
    * 이 인터페이스를 구현하는 인스턴스는 초기화될때 TMDB 에 한번 요청을 날린다.
    * 해당 요청을 통해 TMDB 의 모든 영화장르(20개)를 가져와 필드에 Map 으로 저장해둔다. */
    void initTmdbGenre();
    
    /*
    * 자신의 TmdbGenreMap 에서 id 에 해당하는 장르 이름을 찾고 이를 MovieGenre 객체로 반환한다. */
    MovieGenre getMovieGenre(Integer tmdbGenreId);
    
    
}
