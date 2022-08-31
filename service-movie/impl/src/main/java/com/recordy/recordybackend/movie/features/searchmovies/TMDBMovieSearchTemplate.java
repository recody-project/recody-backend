package com.recordy.recordybackend.movie.features.searchmovies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
class TMDBMovieSearchTemplate implements MovieSearchTemplate{
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    private static final String TMDB_API_KEY_PARAM_NAME = "api_key";
    private static final String TMDB_LANGUAGE_PARAM_NAME = "language";
    private static final String TMDB_QUERY_PARAM_NAME = "query";
    
    
    private static final String TMDB_API_KEY = "1f8f6d6a68057d6e1a0b365156f9d331";
    private static final String TMDB_API_URI = "https://api.themoviedb.org/3/search/movie";
    
    private MovieSearchArguments arguments;
    
    public MovieSearchTemplate language(String language){
        this.arguments.language(language);
        return this;
    }
    
    public MovieSearchTemplate korean(){
        this.arguments.language("ko-KR");
        return this;
    }
    
    public MovieSearchTemplate movieName(String movieName) {
        this.arguments = new MovieSearchArguments();
        this.arguments.movieName(movieName);
        return this;
    }
    
    public String execute(){
        log.debug("Executing movie search: {}", arguments);
        HttpHeaders headers = new HttpHeaders();
    
        URI uri = UriComponentsBuilder.fromUriString(TMDB_API_URI)
                                      .queryParam(TMDB_API_KEY_PARAM_NAME, TMDB_API_KEY)
                                      .queryParam(TMDB_LANGUAGE_PARAM_NAME, arguments.getLanguage())
                                      .queryParam(TMDB_QUERY_PARAM_NAME, arguments.getMovieName())
                                      .encode()
                                      .build()
                                      .toUri();
        log.debug("uri: {}", uri);
        RequestEntity<Void> requestEntity = RequestEntity.get(uri).headers(headers).build();
        return restTemplate.exchange(requestEntity, String.class).getBody();
    }
}
