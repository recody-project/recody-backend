package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.MovieDetail;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;


public interface MovieManager {
    
    /**
     * 받아온 영화 정보를 저장한다. <br>
     * 영화의 제목, 장르, 국가 등의 정보를 체크하고 영화 엔티티와 매핑하여 저장한다. <br>
     * <ul>
     *     <b>업데이트 하는 정보</b>
     * <li> 제목의 경우, 전달받은 Locale 에 따라 저장한다.</li>
     * </ul>
     * @param movieDetail 영화 정보
     * @return 저장된 영화 정보의 고유 id
     */
    MovieEntity register(MovieDetail movieDetail, Locale locale);
    
    MovieEntity register(TMDBMovieSearchNode movie, Locale locale);
    
    List<MovieEntity> registerList(List<TMDBMovieSearchNode> movie, Locale locale);
    
    CompletableFuture<List<MovieEntity>> registerListAsync(List<TMDBMovieSearchNode> movies, Locale locale);
}
