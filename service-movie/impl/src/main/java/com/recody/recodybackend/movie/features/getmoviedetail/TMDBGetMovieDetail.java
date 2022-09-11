package com.recody.recodybackend.movie.features.getmoviedetail;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.recody.recodybackend.common.openapi.APIRequest;
import com.recody.recodybackend.movie.general.TMDBAPIRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TMDBGetMovieDetail implements GetMovieDetail {
    
    private static final String PATH = "/movie/";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final TMDBAPIRequest delegate;
    
    private final String movieId;
    private final String language;
    
    public TMDBGetMovieDetail(String movieId, String language) {
        delegate = new TMDBAPIRequest(PATH + movieId, language);
        this.movieId = movieId;
        this.language = language;
    }
    
    @Override
    public String getMovieId() { return movieId; }
    
    @Override
    public String getLanguage() { return language; }
    
    @Override
    public APIRequest toAPIRequest() {
        log.debug("converting {} into APIRequest", getClass());
        return delegate;
    }
}
