package com.recody.recodybackend.movie.features.searchmovies;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchMovie {
    private String movieName;
    private String language;
}
