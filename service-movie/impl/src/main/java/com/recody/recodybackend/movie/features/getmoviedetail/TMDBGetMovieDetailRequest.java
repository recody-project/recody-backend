package com.recody.recodybackend.movie.features.getmoviedetail;

import com.recody.recodybackend.common.openapi.APIFeature;
import com.recody.recodybackend.common.openapi.APIRequest;
import com.recody.recodybackend.movie.general.TMDBAPIRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TMDBGetMovieDetailRequest implements APIFeature {
    private static final String PATH = "/movie/";
    private static final String TMDB_LANGUAGE_PARAM_NAME = "language";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final TMDBAPIRequest delegate;
    
    private final String movieId;
    private final String language;
    
    public TMDBGetMovieDetailRequest(String movieId, String language) {
        this.movieId = movieId;
        this.language = language;
        delegate = new TMDBAPIRequest();
        delegate.setPath(PATH + movieId);
        delegate.addRequestParam(TMDB_LANGUAGE_PARAM_NAME, language, false);
        
    }
    @Override
    public APIRequest toAPIRequest() {
        return delegate;
    }
}
