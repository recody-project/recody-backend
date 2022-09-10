package com.recody.recodybackend.movie.features.getmovieDetail;

import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;

import java.util.List;

/*
* 받아온 api 데이터에서 어떤 데이터를 꺼내 쓸 지 정의한다. */
public interface GetMovieDetailResponse {
    
    MovieSource getContentSource();
    List<MovieGenre> getGenres();
    String getImdbId();
}
