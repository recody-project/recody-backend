package com.recody.recodybackend.movie.features.getmovieDetail;

import com.recody.recodybackend.common.openapi.APIRequest;
import com.recody.recodybackend.common.openapi.APIRequester;
import com.recody.recodybackend.common.openapi.JsonAPIResponse;
import com.recody.recodybackend.common.openapi.Request;
import com.recody.recodybackend.movie.general.TMDBAPIRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
class TMDBGetMovieDetailHandler implements GetMovieDetailHandler {
    
    private final APIRequester<TMDBAPIRequest> requester;
    
    @Override
    public String handleToString(GetMovieDetail request) {
        String body;
        body = requester.requestToString(request);
        return body;
    }
    
    @Override
    public GetMovieDetailResponse handleToResponse(GetMovieDetail request) {
        JsonAPIResponse body;
        body = requester.requestToJson(request);
        return new TMDBGetMovieDetailResponse(body);
    }
    
    @Override
    public JsonAPIResponse handleToJson(GetMovieDetail request) {
        JsonAPIResponse body;
        body = requester.requestToJson(request);
        return body;
    }
}
