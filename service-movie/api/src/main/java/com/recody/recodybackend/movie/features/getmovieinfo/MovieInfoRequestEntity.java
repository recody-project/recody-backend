package com.recody.recodybackend.movie.features.getmovieinfo;

import org.springframework.http.RequestEntity;

public interface MovieInfoRequestEntity {
    
    RequestEntity<Void> toEntity();
    
    void setApiKey(String apiKey);
    
    String getLanguage();
}
