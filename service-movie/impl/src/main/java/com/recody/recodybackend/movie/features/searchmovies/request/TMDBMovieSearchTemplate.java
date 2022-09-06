package com.recody.recodybackend.movie.features.searchmovies.request;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Component
@Slf4j
class TMDBMovieSearchTemplate implements MovieSearchTemplate {
    
    private final String TMDB_API_KEY;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    public TMDBMovieSearchTemplate(@Value("${movie.tmdb.api-key}") String apiKey) {
        this.TMDB_API_KEY = apiKey;
    }
    
    public String execute(MovieSearchRequestEntity request){
        request.setApiKey(TMDB_API_KEY);
        log.debug("Requesting movie search to TMDB: {}", request);
        RequestEntity<Void> requestEntity = request.toEntity();
        String body;
        try {
            body = restTemplate.exchange(requestEntity, String.class).getBody();
        } catch (Exception exception){
            log.warn("FAILED to get data from TMDB: {}", exception.getMessage());
            throw new RuntimeException("영화 정보를 가져오는데에 실패하였습니다.");
        }
        return body;
    }
    
    @Override
    public JsonTMDBMovieSearchResult executeToJson(MovieSearchRequestEntity request) {
        request.setApiKey(TMDB_API_KEY);
        log.debug("Requesting movie search to TMDB: {}", request);
        RequestEntity<Void> requestEntity = request.toEntity();
        JsonNode body;
        try {
            body = restTemplate.exchange(requestEntity, JsonNode.class).getBody();
        } catch (Exception exception){
            log.warn("FAILED to get data from TMDB: {}", exception.getMessage());
            throw new RuntimeException("영화 정보를 가져오는데에 실패하였습니다.");
        }
        // root id, requested language, genre 남음.
        Locale locale;
        try {
            locale = new Locale(request.getLanguage());
        } catch (Exception exception) {
            throw new RuntimeException("language 파라미터가 올바르지 않습니다.");
        }
        return JsonTMDBMovieSearchResult.builder()
                                        .requestLocale(locale)
                                        .response(body)
                                        .build();
    }
}
