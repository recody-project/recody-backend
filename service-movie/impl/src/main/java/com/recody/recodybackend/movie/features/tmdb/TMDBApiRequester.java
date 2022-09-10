package com.recody.recodybackend.movie.features.tmdb;

import com.fasterxml.jackson.databind.JsonNode;
import com.recody.recodybackend.common.openapi.ApiRequester;
import com.recody.recodybackend.common.openapi.JsonApiResponse;
import com.recody.recodybackend.movie.general.TMDBRequestEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class TMDBApiRequester implements ApiRequester<TMDBRequestEntity> {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiKey;
    
    public TMDBApiRequester(@Value("${movie.tmdb.api-key}") String apikey) {
        this.apiKey = apikey;
    }
    
    @Override
    public String executeToString(TMDBRequestEntity request) {
        String body;
        request.setApiKey(apiKey);
        try{
            body = restTemplate.exchange(request.toEntity(), String.class).getBody();
        } catch (Exception exception){
            throw new RuntimeException("TMDB 에서 정보를 받아오는 데에 실패하였습니다.");
        }
        return body;
    }
    
    @Override
    public JsonNode executeToJsonNode(TMDBRequestEntity request){
        JsonNode body;
        request.setApiKey(apiKey);
        try{
            body = restTemplate.exchange(request.toEntity(), JsonNode.class).getBody();
        } catch (Exception exception){
            throw new RuntimeException("TMDB 에서 정보를 받아오는 데에 실패하였습니다.");
        }
        return body;
    }
    
    @Override
    public JsonApiResponse executeToJson(TMDBRequestEntity request) {
        log.info("request: {}", request);
        request.setApiKey(apiKey);
        JsonNode body;
        try{
            log.info("request: {}", request);
            log.info("apiKey: {}", apiKey);
            body = restTemplate.exchange(request.toEntity(), JsonNode.class).getBody();
        } catch (Exception exception){
            throw new RuntimeException("TMDB 에서 정보를 받아오는 데에 실패하였습니다.");
        }
        return JsonApiResponse.of(body);
    }
}
