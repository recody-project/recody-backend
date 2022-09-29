package com.recody.recodybackend.catalog.features.api.movie;

import com.recody.recodybackend.common.openapi.APIRequest;
import com.recody.recodybackend.common.openapi.AbstractAPIRequest;

public class MovieAPIRequest extends AbstractAPIRequest implements APIRequest {
    private static final String movieApiBaseUrl = "http://127.0.0.1:8140";
    
    public MovieAPIRequest() {
        super(movieApiBaseUrl);
    }
    
    public MovieAPIRequest(String baseUrl) {
        super(baseUrl);
    }
}
