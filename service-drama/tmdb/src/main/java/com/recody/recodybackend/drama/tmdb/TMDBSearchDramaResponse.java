package com.recody.recodybackend.drama.tmdb;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TMDBSearchDramaResponse {

    private Integer page;
    
    private List<TMDBDrama> results;

    @JsonAlias("total_pages")
    private Integer totalPages;

    @Override
    public String toString() {
        return "{\"TMDBSearchDramaResponse\":{"
                + "\"page\":" + page
                + ", \"results\":" + results
                + ", \"totalPages\":" + totalPages
                + "}}";
    }
}
