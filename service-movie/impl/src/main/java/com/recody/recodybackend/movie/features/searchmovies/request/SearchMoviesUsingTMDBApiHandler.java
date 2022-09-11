package com.recody.recodybackend.movie.features.searchmovies.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.openapi.APIRequester;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesUsingApi;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesUsingApiHandler;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesUsingApiResponse;
import com.recody.recodybackend.movie.general.TMDBAPIRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class SearchMoviesUsingTMDBApiHandler implements SearchMoviesUsingApiHandler {
    
    private final APIRequester<TMDBAPIRequest> requester;
    
    public String handleToString(SearchMoviesUsingApi request){
        log.debug("Requesting movie search to TMDB: {}", request);
        String body;
        body = requester.requestToString(request);
        return body;
    }
    
    @Override
    public SearchMoviesUsingApiResponse handleToJson(SearchMoviesUsingApi request) {
        log.debug("Requesting movie search to TMDB: {}", request);
        JsonNode body;
        body = requester.requestToJsonNode(request);
        return SearchMoviesUsingTMDBApiResponse.builder()
                                               .jsonNode(body)
                                               .build();
    }
}
