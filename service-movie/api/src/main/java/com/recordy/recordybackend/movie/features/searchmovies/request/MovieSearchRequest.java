package com.recordy.recordybackend.movie.features.searchmovies.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
public abstract class MovieSearchRequest{
    
    private final String API_KEY_PARAM_NAME;
    private final String SEARCH_PARAM_NAME;
    private final String LANGUAGE_PARAM_NAME;
    protected final UriComponentsBuilder uriComponentsBuilder;
    protected final HttpHeaders headers = new HttpHeaders();
    
    @Getter
    private String movieName;
    @Getter
    private String language;
    @Getter(AccessLevel.PRIVATE)
    private String apiKey;
    
    protected MovieSearchRequest(String uri, String apiKeyParamName, String searchParamName, String languageParamName) {
        this.API_KEY_PARAM_NAME = apiKeyParamName;
        this.SEARCH_PARAM_NAME = searchParamName;
        this.LANGUAGE_PARAM_NAME = languageParamName;
        this.uriComponentsBuilder = UriComponentsBuilder.fromUriString(uri);
    }
    
    public RequestEntity<Void> toEntity() {
        validate();
        URI uri = createUri();
        return makeEntity(uri, headers);
    }
    
    protected void setMovieName(String movieName){
        this.movieName = movieName;
        this.uriComponentsBuilder.queryParam(SEARCH_PARAM_NAME, movieName);
        log.trace("movieName set to: {}", movieName);
    }
    
    protected void setLanguage(String language){
        this.language = language;
        this.uriComponentsBuilder.queryParam(LANGUAGE_PARAM_NAME, language);
        log.trace("movieName set to: {}", movieName);
    }
    
    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
        uriComponentsBuilder.queryParam(API_KEY_PARAM_NAME, apiKey);
        log.trace("api key has been set");
    }
    
    protected abstract RequestEntity<Void> makeEntity(URI uri, HttpHeaders headers);
    
    private URI createUri() {
        return this.uriComponentsBuilder.encode().build().toUri();
    }
    
    private void validate() {
        if (movieName == null) throw new IllegalArgumentException("Movie name was not set");
        if (language == null) throw new IllegalArgumentException("Language was not set");
        if (apiKey == null) throw new IllegalArgumentException("Api Key was not set");
    }
    
    @Override
    public String toString() {
        return "{\"MovieSearchRequest\":{" + "\"movieName\":" + ((movieName != null) ? ("\"" + movieName + "\"") : null) + ", \"language\":" + ((language != null) ? ("\"" + language + "\"") : null) + "}}";
    }
}
