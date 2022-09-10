package com.recody.recodybackend.movie.features.getmovieDetail;

import com.recody.recodybackend.common.openapi.ApiRequester;
import com.recody.recodybackend.common.openapi.JsonApiResponse;
import com.recody.recodybackend.movie.general.TMDBRequestEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class TMDBGetMovieDetailHandler implements GetMovieDetailHandler {
    
    private final ApiRequester<TMDBRequestEntity> requester;
    
    @Override
    public String handleToString(GetMovieDetail request) {
        String body;
        body = requester.executeToString(request.toEntity());
        return body;
    }
    
    @Override
    public GetMovieDetailResponse handleToResponse(GetMovieDetail request) {
        JsonApiResponse body;
        body = requester.executeToJson(request.toEntity());
        return new TMDBGetMovieDetailResponse(body);
    }
    
    @Override
    public JsonApiResponse handleToJson(GetMovieDetail request) {
        JsonApiResponse body;
        body = requester.executeToJson(request.toEntity());
        return body;
    }
}
