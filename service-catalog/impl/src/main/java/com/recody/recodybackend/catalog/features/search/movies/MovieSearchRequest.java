package com.recody.recodybackend.catalog.features.search.movies;

import com.recody.recodybackend.catalog.features.api.movie.MovieAPIRequest;
import com.recody.recodybackend.common.openapi.APIFeature;
import com.recody.recodybackend.common.openapi.APIRequest;

public class MovieSearchRequest implements APIFeature {
    
    private final MovieAPIRequest request;
    private String path = "/api/v1/movie/search";
    private String MOVIE_SEARCH_PARAM_NAME = "movieName";
    private String LANGUAGE_PARAM_NAME = "language";
    
    public MovieSearchRequest(String keyword, String language) {
        this.request = new MovieAPIRequest();
        request.setPath(path);
        request.addRequestParam(MOVIE_SEARCH_PARAM_NAME, keyword);
        request.addRequestParam(LANGUAGE_PARAM_NAME, language, false);
    }
    
    @Override
    public APIRequest toAPIRequest() {
        return request;
    }
}
