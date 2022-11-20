package com.recody.recodybackend.movie.web;

import com.recody.recodybackend.common.data.QueryMetadata;
import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SearchMoviesResult {
    
    private QueryMetadata metadata;
    
    private List<TMDBSearchedMovie> movies;
    
    @Override
    public String toString() {
        return "{\"SearchMoviesResult\":{"
               + "\"metadata\":" + metadata
               + ", \"movies\":" + movies
               + "}}";
    }
}
