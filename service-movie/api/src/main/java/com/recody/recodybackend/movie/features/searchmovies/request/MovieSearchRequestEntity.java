package com.recody.recodybackend.movie.features.searchmovies.request;

import com.recody.recodybackend.common.openapi.RecodyRequestEntity;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
public abstract class MovieSearchRequestEntity extends RecodyRequestEntity {
    
    private final String SEARCH_PARAM_NAME;
    protected final UriComponentsBuilder uriComponentsBuilder;
    protected final HttpHeaders headers = new HttpHeaders();
    
    @Getter
    private String movieName;
    
    protected MovieSearchRequestEntity(String uri, String apiKeyParamName, String searchParamName, String languageParamName) {
        super(apiKeyParamName, languageParamName);
        this.SEARCH_PARAM_NAME = searchParamName;
        this.uriComponentsBuilder = UriComponentsBuilder.fromUriString(uri);
    }
    
    @Override
    public final RequestEntity<Void> toEntity() {
        validate();
        URI uri = createUri();
        return makeEntity(uri, headers);
    }
    
    protected void setMovieName(String movieName){
        this.movieName = movieName;
        this.uriComponentsBuilder.queryParam(SEARCH_PARAM_NAME, movieName);
        log.trace("movieName set to: {}", movieName);
    }
    
    @Override
    protected void setLanguage(String language){
        this.language = language;
        this.uriComponentsBuilder.queryParam(LANGUAGE_PARAM_NAME, language);
        log.trace("movieName set to: {}", movieName);
    }
    
    @Override
    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
        uriComponentsBuilder.queryParam(API_KEY_PARAM_NAME, apiKey);
        log.trace("api key has been set");
    }
    
    protected abstract RequestEntity<Void> makeEntity(URI uri, HttpHeaders headers);
    
    protected URI createUri() {
        return this.uriComponentsBuilder.encode().build().toUri();
    }
    
    protected void validate() {
        if (movieName == null) throw new IllegalArgumentException("Movie name was not set");
        if (language == null) throw new IllegalArgumentException("Language was not set");
        if (apiKey == null) throw new IllegalArgumentException("Api Key was not set");
    }
    
    @Override
    public String toString() {
        return "{\"MovieSearchRequest\":{" + "\"movieName\":" + ((movieName != null) ? ("\"" + movieName + "\"") : null) + ", \"language\":" + ((language != null) ? ("\"" + language + "\"") : null) + "}}";
    }
}
