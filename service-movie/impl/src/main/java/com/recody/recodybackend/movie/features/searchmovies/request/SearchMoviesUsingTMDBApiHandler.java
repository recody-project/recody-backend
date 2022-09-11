package com.recody.recodybackend.movie.features.searchmovies.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesUsingApi;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesUsingApiHandler;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesUsingApiResponse;
import com.recody.recodybackend.movie.features.tmdb.TMDBAPIRequester;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class SearchMoviesUsingTMDBApiHandler implements SearchMoviesUsingApiHandler {
    
    private final TMDBAPIRequester requester;
    
    public String handleToString(SearchMoviesUsingApi request){
        log.debug("Requesting movie search to TMDB: {}", request);
        String body;
        body = requester.executeToString(request.toAPIRequest());
        return body;
    }
    
    @Override
    public SearchMoviesUsingApiResponse handleToJson(SearchMoviesUsingApi request) {
        log.debug("Requesting movie search to TMDB: {}", request);
        JsonNode body;
        body = requester.executeToJsonNode(request.toAPIRequest());
        return SearchMoviesUsingTMDBApiResponse.builder()
                                               .jsonNode(body)
                                               .build();
    }
}
