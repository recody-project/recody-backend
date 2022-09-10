package com.recody.recodybackend.movie.features.test;

import com.recody.recodybackend.movie.general.TMDBRequestEntity;

public class NewTMDBMovieDetailRequest extends TMDBRequestEntity {
    
    private static final String PATH = "/movie/";
    
    public NewTMDBMovieDetailRequest(String movieId, String language) {
        super(PATH + movieId, language);
    }
}
