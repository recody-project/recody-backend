package com.recody.recodybackend.catalog.features.search.movies;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchMovies {
    
    private String keyword;
    private String language;
    private List<String> genreIds;
    
}
