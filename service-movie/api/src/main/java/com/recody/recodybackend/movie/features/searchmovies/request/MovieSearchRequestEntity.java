package com.recody.recodybackend.movie.features.searchmovies.request;

import org.springframework.http.RequestEntity;

public interface MovieSearchRequestEntity {
    
    RequestEntity<Void> toEntity();
    
    String getLanguage();
    
    void setApiKey(String apiKey);
}
