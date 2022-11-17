package com.recody.recodybackend.movie.web;

import com.recody.recodybackend.movie.Movie;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SearchMoviesByQueryResult {
    private Locale requestedLanguage;
    private List<Movie> movies;
    private Integer currentPage;
    private Integer size;
    
    private Integer totalPages;
    
    @Override
    public String toString() {
        return "{\"SearchMoviesByQueryResult\":{" + "\"requestedLanguage\":" + requestedLanguage + ", \"movies\":" + movies + ", \"page\":" + currentPage + ", \"total\":" + size + "}}";
    }
}
