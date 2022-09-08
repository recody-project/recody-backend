package com.recody.recodybackend.common.openapi;

import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public abstract class AbstractRequestEntity {
    private final UriComponentsBuilder uriComponentsBuilder;
    private final HttpHeaders headers = new HttpHeaders();
    protected final String API_KEY_PARAM_NAME;
    protected final String LANGUAGE_PARAM_NAME;
    protected String language;
    protected String apiKey;
    
    
    public AbstractRequestEntity(String uri, String API_KEY_PARAM_NAME, String LANGUAGE_PARAM_NAME) {
        this.API_KEY_PARAM_NAME = API_KEY_PARAM_NAME;
        this.LANGUAGE_PARAM_NAME = LANGUAGE_PARAM_NAME;
        this.uriComponentsBuilder = UriComponentsBuilder.fromUriString(uri);
    }
    
    public final void setPath(String path){
        this.uriComponentsBuilder.path(path);
    }
    
    public final RequestEntity<Void> toEntity(){
        validateApiKeyAndLanguage();
        URI uri = createUri();
        return makeEntity(uri, headers);
    }
    
    public final void addRequestParam(String key, String value){
        uriComponentsBuilder.queryParam(key, value);
    }
    
    public final void addHeader(String key, String value){
        headers.add(key, value);
    }
    
    public String getLanguage() { return language; }
    
    // Never open
    private String getApiKey() { return apiKey; }
    
    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
        addRequestParam(API_KEY_PARAM_NAME, apiKey);
    }
    
    public void setLanguage(String language){
        this.language = language;
        addRequestParam(LANGUAGE_PARAM_NAME, language);
    }
    
    protected URI createUri() {
        return this.uriComponentsBuilder.encode().build().toUri();
    }
    
    protected RequestEntity<Void> makeEntity(URI uri, HttpHeaders headers){
        return RequestEntity.get(uri).headers(headers).build();
    }
    
    private void validateApiKeyAndLanguage(){
        if (language == null) throw new IllegalArgumentException("Language was not set");
        if (apiKey == null) throw new IllegalArgumentException("Api Key was not set");
    }
}
