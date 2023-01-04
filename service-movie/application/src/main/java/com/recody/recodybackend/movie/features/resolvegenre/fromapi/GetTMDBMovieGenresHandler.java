package com.recody.recodybackend.movie.features.resolvegenre.fromapi;

import com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.dto.TMDBMovieGenre;

import java.util.List;

/**
 * TMDB 에서 영화 장르를 가져와 반환한다.
 * @author motive
 */
public interface GetTMDBMovieGenresHandler {
    
    List<TMDBMovieGenre> getTMDBMovieGenres();
    
}
