package com.recody.recodybackend.movie.features.resolvegenre.fromapi;

import com.recody.recodybackend.movie.general.TMDBAPIRequest;

public class TMDBGenreAPIRequest extends TMDBAPIRequest {
    
    private static final String GENRE_PATH = "/genre/movie/list";
    
    public TMDBGenreAPIRequest() {
        super(GENRE_PATH);
    }
}
