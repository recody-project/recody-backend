package com.recody.recodybackend.common.openapi;

import org.springframework.http.RequestEntity;

public abstract class RecodyRequestEntity {
    protected final String API_KEY_PARAM_NAME;
    protected final String LANGUAGE_PARAM_NAME;
    protected String language;
    protected String apiKey;
    
    public RecodyRequestEntity(String API_KEY_PARAM_NAME, String LANGUAGE_PARAM_NAME) {
        this.API_KEY_PARAM_NAME = API_KEY_PARAM_NAME;
        this.LANGUAGE_PARAM_NAME = LANGUAGE_PARAM_NAME;
    }
    
    public abstract RequestEntity<Void> toEntity();
    
    public String getLanguage() { return language; }
    
    // Never open
    private String getApiKey() { return apiKey; }
    
    protected abstract void setLanguage(String language);
    
    public abstract void setApiKey(String apiKey);
}
