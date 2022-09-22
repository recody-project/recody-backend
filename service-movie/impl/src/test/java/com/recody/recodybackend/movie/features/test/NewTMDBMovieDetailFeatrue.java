package com.recody.recodybackend.movie.features.test;

import com.recody.recodybackend.common.openapi.APIFeature;
import com.recody.recodybackend.common.openapi.APIRequest;
import com.recody.recodybackend.movie.general.TMDBAPIRequest;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

public class NewTMDBMovieDetailFeatrue implements APIFeature {
    
    private static final String PATH = "/movie/";
    private final APIRequest delegate;
    private static final String TMDB_LANGUAGE_PARAM_NAME = "language";
    
    public NewTMDBMovieDetailFeatrue(String movieId, String language) {
        delegate = new TMDBAPIRequest();
        delegate.setPath(PATH + movieId);
        delegate.addRequestParam(TMDB_LANGUAGE_PARAM_NAME, language, false);
        
    }
    
    @Override
    public APIRequest toAPIRequest() {
        return delegate;
    }
    
    @Override
    public String toString() {
        return "[{\"NewTMDBMovieDetailAPIRequest\":{" + "\"path\":" + ((path != null) ? ("\"" + path + "\"") : null) + "}}, " + super.toString() + "]";
    }
}
