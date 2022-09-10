package com.recody.recodybackend.movie.features.searchmovies;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Locale;

@Builder
@Data
public class SearchMoviesResult {
    // 요청 메타 데이터
    private Locale requestedLanguage;
    
    // 요청 결과
    private List<SingleMovieSpec> movies;
    
    private Integer page;
    private Integer total;
}
