package com.recody.recodybackend.catalog.features.api.movie;

import com.recody.recodybackend.common.openapi.APIRequestFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class MovieAPIRequestFactory implements APIRequestFactory<MovieAPIRequest> {
    
    private final String baseUrl;
    
    MovieAPIRequestFactory(@Value("${catalog.movie.search.base-url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    public String getBaseUrl() {
        return baseUrl;
    }
    
    @Override
    public MovieAPIRequest newRequest() {
        return new MovieAPIRequest(baseUrl);
    }
}
