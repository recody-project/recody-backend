package com.recody.recodybackend.movie.features.resolvegenres;

import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieGenre;
import com.recody.recodybackend.movie.general.MovieGenre;

import java.util.List;

public interface MovieGenreResolver {
    
    /**
     * TMDB 영화 장르 코드를 id 와 name 을 함께 반환한다.
     * @param tmdbGenreId tmdb 영화 장르 고유 코드
     * @return TMDB 영화 장르 코드
     * @author motive
     */
    TMDBMovieGenre toTMDBGenre(Integer tmdbGenreId);
    
    List<TMDBMovieGenre> toTMDBGenres(List<Integer> tmdbGenreId);
    
    MovieGenre toMovieGenre(Integer tmdbGenreId);
    
    
    
    List<MovieGenre> toMovieGenreList(List<Integer> tmdbGenreIds);
    
    
    /**
     * @param genreId Recody Movie 서비스에서 부여한 영화 장르 코드
     * @return 영화 장르 객체
     * @author motive
     */
    MovieGenre toMovieGenre(String genreId);
    
    /**
     * 서버가 뜰 때 영화 장르 리스트를 체크한다.
     * @author motive
     */
    void initTMDBGenres();
    
}
