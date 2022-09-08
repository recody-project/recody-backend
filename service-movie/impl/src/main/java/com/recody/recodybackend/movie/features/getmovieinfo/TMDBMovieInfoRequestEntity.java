package com.recody.recodybackend.movie.features.getmovieinfo;

import com.recody.recodybackend.movie.features.searchmovies.request.TMDBRequestEntity;

public class TMDBMovieInfoRequestEntity extends TMDBRequestEntity implements MovieInfoRequestEntity {
    
    private static final String PATH = "/movie/";
    
    public TMDBMovieInfoRequestEntity(String movieId, String language) {
        super(PATH + movieId);
        super.setLanguage(language);
    }
}
