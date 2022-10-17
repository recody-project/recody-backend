package com.recody.recodybackend.movie.features.getmoviecredit;

import lombok.Data;

@Data
public class Director {
    private Long id;
    
    private Long tmdbId;
    private String name;
    
    private String profilePath;
    
    @Override
    public String toString() {
        return "{\"Director\":{" + "\"id\":" + id + ", \"tmdbId\":" + tmdbId + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null) + ", \"profilePath\":" + ((profilePath != null) ? ("\"" + profilePath + "\"") : null) + "}}";
    }
}
