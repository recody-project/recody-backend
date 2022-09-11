package com.recody.recodybackend.movie.features.getmoviedetail;

import com.recody.recodybackend.movie.general.MovieGenre;
import com.recody.recodybackend.movie.general.MovieSource;

import java.util.List;

/*
* 받아온 api 데이터에서 어떤 데이터를 꺼내 쓸 지 정의한다. */
public interface GetMovieDetailResponse {
    
    MovieSource getContentSource();
    List<MovieGenre> getGenres();
    Integer getId();
    String getImdbId(); // nullable, length: 9
    String getOriginalLanguage();
    String getOriginalTitle();
    String getOverview(); // nullable
    Float getPopularity();
    String getPosterPath();
    List<ProductionCountry> getProductionCountries();
    String getReleaseDate(); // date format
    Integer getRuntime(); // nullable
    Integer getRevenue();
    List<SpokenLanguage> getSpokenLanguages();
    String getStatus(); // enum
    String getTitle();
    Float getVoteAverage();
    Integer getVoteCount();
    
}
