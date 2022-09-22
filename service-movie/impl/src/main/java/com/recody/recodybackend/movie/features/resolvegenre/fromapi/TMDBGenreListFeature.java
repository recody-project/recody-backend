package com.recody.recodybackend.movie.features.resolvegenre.fromapi;

import com.recody.recodybackend.common.openapi.APIRequest;
import com.recody.recodybackend.common.openapi.APIFeature;
import com.recody.recodybackend.movie.general.TMDBAPIRequest;

public class TMDBGenreListFeature implements APIFeature {
    
    private static final String GENRE_PATH = "/genre/movie/list";
    private final APIRequest request = new TMDBAPIRequest();
    
    public TMDBGenreListFeature() {
        request.setPath(GENRE_PATH);
    }
    
    @Override
    public APIRequest toAPIRequest() {
        return request;
    }
}
