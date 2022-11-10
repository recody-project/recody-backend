package com.recody.recodybackend.catalog.features.search.movies;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchMovies {
    
    private String keyword;
    private String language;
    
}
