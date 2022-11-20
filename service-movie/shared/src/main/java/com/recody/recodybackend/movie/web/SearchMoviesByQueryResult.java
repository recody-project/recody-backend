package com.recody.recodybackend.movie.web;

import com.recody.recodybackend.common.data.QueryMetadata;
import com.recody.recodybackend.movie.Movie;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SearchMoviesByQueryResult {
    
    private QueryMetadata metadata;
    private List<Movie> movies;
    
    @Override
    public String toString() {
        return "{\"SearchMoviesByQueryResult\":{"
               + "\"metadata\":" + metadata
               + ", \"movies\":" + movies
               + "}}";
    }
}
