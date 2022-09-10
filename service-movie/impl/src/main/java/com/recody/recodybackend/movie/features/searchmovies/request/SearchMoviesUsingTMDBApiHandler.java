package com.recody.recodybackend.movie.features.searchmovies.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.movie.features.tmdb.TMDBApiRequester;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class SearchMoviesUsingTMDBApiHandler implements SearchMoviesUsingApiHandler {
    
    private final TMDBApiRequester requester;
    
    public String handleToString(SearchMoviesUsingApi request){
        log.debug("Requesting movie search to TMDB: {}", request);
        String body;
        body = requester.executeToString(request.toEntity());
        return body;
    }
    
    @Override
    public SearchMoviesUsingApiResponse handleToJson(SearchMoviesUsingApi request) {
        log.debug("Requesting movie search to TMDB: {}", request);
        JsonNode body;
        body = requester.executeToJsonNode(request.toEntity());
        return SearchMoviesUsingTMDBApiResponse.builder()
                                               .jsonNode(body)
                                               .build();
    }
}
