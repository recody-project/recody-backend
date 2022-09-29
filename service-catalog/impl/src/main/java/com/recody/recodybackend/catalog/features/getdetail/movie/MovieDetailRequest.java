package com.recody.recodybackend.catalog.features.getdetail.movie;

import com.recody.recodybackend.catalog.features.api.movie.MovieAPIRequest;
import com.recody.recodybackend.common.openapi.APIFeature;
import com.recody.recodybackend.common.openapi.APIRequest;
import com.recody.recodybackend.common.openapi.APIRequestFactory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

public class MovieDetailRequest implements APIFeature {
    
    private final MovieAPIRequest request;
    @Getter
    private String path = "/api/v1/movie/detail";
    @Getter
    private String MovieId ="movieId";
    
    private String LANGUAGE = "language";
    
    public MovieDetailRequest(String movieId) {
        this.request = new MovieAPIRequest();
        request.setPath(path);
        request.addRequestParam(MovieId, movieId);
    }
    
    public MovieDetailRequest(MovieAPIRequest apiRequest, String movieId, String language) {
        this.request = apiRequest;
        apiRequest.setPath(path);
        apiRequest.addRequestParam(MovieId, movieId);
        apiRequest.addRequestParam(LANGUAGE, language, false);
    }
    
    @Override
    public APIRequest toAPIRequest() {
        return request;
    }
}
