package com.recody.recodybackend.movie.web;

import lombok.Data;

@Data
public class TMDBSearchedMovie {
    
    
    private Integer tmdbId;
    
    private String posterPath;
    
    private String title;
    
    @Override
    public String toString() {
        return "{\"TMDBSearchedMovie\":{" + "\"tmdbId\":" + tmdbId + ", \"posterPath\":" + ((posterPath != null) ? ("\"" + posterPath + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + "}}";
    }
}
