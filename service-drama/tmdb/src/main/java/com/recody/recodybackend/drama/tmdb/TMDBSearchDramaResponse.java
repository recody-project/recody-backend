package com.recody.recodybackend.drama.tmdb;

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
    
    @Override
    public String toString() {
        return "{\"TMDBSearchDramaResponse\":{"
               + "\"page\":" + page
               + ", \"results\":" + results
               + "}}";
    }
}
