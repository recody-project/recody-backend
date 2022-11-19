package com.recody.recodybackend.movie.features.searchmovies.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

@Data
public class TMDBMovieSearchResponse {

    private Integer page;
    private List<TMDBMovieSearchNode> results;
    
    @JsonAlias("total_pages")
    private Integer totalPages;
    @JsonAlias("total_results")
    private Integer totalResults;
    
    @Override
    public String toString() {
        return "{\"TMDBMovieSearchResponse\":{" + "\"page\":" + page + ", \"results\":" + results + ", \"totalPages\":" + totalPages + ", \"totalResults\":" + totalResults + "}}";
    }
}
