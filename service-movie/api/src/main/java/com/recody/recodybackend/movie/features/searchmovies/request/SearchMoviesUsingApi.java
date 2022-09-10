package com.recody.recodybackend.movie.features.searchmovies.request;

import com.recody.recodybackend.common.openapi.Request;
import com.recody.recodybackend.movie.general.TMDBRequestEntity;

public interface SearchMoviesUsingApi extends Request<TMDBRequestEntity> {
    
    TMDBRequestEntity toEntity();
    
    String getLanguage();
    
    void setApiKey(String apiKey);
}
