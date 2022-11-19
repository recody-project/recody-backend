package com.recody.recodybackend.movie.features.tmdb;

import lombok.Value;

@Value(staticConstructor = "of")
public class TMDBMovieID {
    Integer id;
    
    @Override
    public String toString() {
        return "{\"TMDBMovieID\":{" + "\"id\":" + id + "}}";
    }
}
