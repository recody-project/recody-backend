package com.recody.recodybackend.movie.features.getmovieinfo;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.openapi.ApiResponse;
import com.recody.recodybackend.movie.general.MovieSource;

public interface MovieInfoResponse extends ApiResponse<JsonNode> {
    
    @Override
    MovieSource getContentSource();
}
