package com.recody.recodybackend.movie.features.getmovieDetail;

import com.recody.recodybackend.common.openapi.APIRequest;
import com.recody.recodybackend.movie.general.TMDBAPIRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TMDBGetMovieDetail implements GetMovieDetail {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String PATH = "/movie/";
    private final TMDBAPIRequest delegate;
    
    public TMDBGetMovieDetail(String movieId, String language) {
        delegate = new TMDBAPIRequest(PATH + movieId, language);
    }
    
    @Override
    public APIRequest toAPIRequest() {
        log.debug("converting : {}", log);
        return delegate;
    }
}
