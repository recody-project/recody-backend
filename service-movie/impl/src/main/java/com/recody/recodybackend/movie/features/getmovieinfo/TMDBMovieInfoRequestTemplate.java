package com.recody.recodybackend.movie.features.getmovieinfo;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
class TMDBMovieInfoRequestTemplate implements MovieInfoRequestTemplate {
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiKey;
    
    public TMDBMovieInfoRequestTemplate(@Value("${movie.tmdb.api-key}") String apikey) {
        this.apiKey = apikey;
    }
    
    @Override
    public String executeToString(MovieInfoRequestEntity request) {
        request.setApiKey(apiKey);
        String body;
        try {
            body = restTemplate.exchange(request.toEntity(), String.class).getBody();
        } catch (Exception exception){
            log.warn("FAILED to get data from TMDB: {}", exception.getMessage());
            throw new RuntimeException("영화 정보를 가져오는데에 실패하였습니다.");
        }
        return body;
    }
    
    @Override
    public MovieInfoResponse executeToJson(MovieInfoRequestEntity request) {
        request.setApiKey(apiKey);
        JsonNode body;
        try {
            body = restTemplate.exchange(request.toEntity(), JsonNode.class).getBody();
        } catch (Exception exception){
            log.warn("FAILED to get data from TMDB: {}", exception.getMessage());
            throw new RuntimeException("영화 정보를 가져오는데에 실패하였습니다.");
        }
        return new TMDBMovieInfoResponse(body);
    }
}
