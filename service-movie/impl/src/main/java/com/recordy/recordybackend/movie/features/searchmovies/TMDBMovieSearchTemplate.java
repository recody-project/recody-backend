package com.recordy.recordybackend.movie.features.searchmovies;

import com.recordy.recordybackend.movie.features.searchmovies.request.MovieSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
class TMDBMovieSearchTemplate implements MovieSearchTemplate{
    
    private final String TMDB_API_KEY;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    public TMDBMovieSearchTemplate(@Value("${movie.tmdb.api-key}") String apiKey) {
        this.TMDB_API_KEY = apiKey;
    }
    
    public String execute(MovieSearchRequest request){
        request.setApiKey(TMDB_API_KEY);
        log.debug("Requesting movie search to TMDB: {}", request);
        return restTemplate.exchange(request.toEntity(), String.class).getBody();
    }
}
