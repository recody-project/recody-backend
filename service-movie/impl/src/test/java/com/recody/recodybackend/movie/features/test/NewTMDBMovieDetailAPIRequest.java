package com.recody.recodybackend.movie.features.test;

import com.recody.recodybackend.movie.general.TMDBAPIRequest;

public class NewTMDBMovieDetailAPIRequest extends TMDBAPIRequest {
    
    private static final String PATH = "/movie/";
    
    public NewTMDBMovieDetailAPIRequest(String movieId, String language) {
        super(PATH + movieId, language);
    }
    
    @Override
    public String toString() {
        return "[{\"NewTMDBMovieDetailAPIRequest\":{" + "\"path\":" + ((path != null) ? ("\"" + path + "\"") : null) + "}}, " + super.toString() + "]";
    }
}
