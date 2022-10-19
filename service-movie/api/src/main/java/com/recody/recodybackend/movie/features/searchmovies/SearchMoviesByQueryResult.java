package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.movie.Movie;
import lombok.*;

import java.util.List;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SearchMoviesByQueryResult {
    private Locale requestedLanguage;
    private List<Movie> movies;
    private Integer page;
    private Integer total;
    
    @Override
    public String toString() {
        return "{\"SearchMoviesByQueryResult\":{" + "\"requestedLanguage\":" + requestedLanguage + ", \"movies\":" + movies + ", \"page\":" + page + ", \"total\":" + total + "}}";
    }
}
