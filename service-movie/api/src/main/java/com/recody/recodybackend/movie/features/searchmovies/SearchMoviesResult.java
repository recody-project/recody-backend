package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.movie.web.TMDBSearchedMovie;
import lombok.*;

import java.util.List;
import java.util.Locale;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SearchMoviesResult {
    // 요청 메타 데이터
    private Locale requestedLanguage;
    
    // 요청 결과
    private List<TMDBSearchedMovie> movies;
    
    private Integer page;
    private Integer total;
    
    @Override
    public String toString() {
        return "{\"SearchMoviesResult\":{" + "\"requestedLanguage\":" + requestedLanguage + ", \"movies\":" + movies + ", \"page\":" + page + ", \"total\":" + total + "}}";
    }
}
