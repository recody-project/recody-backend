package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.common.openapi.Request;
import com.recody.recodybackend.movie.general.TMDBAPIRequest;

public interface SearchMoviesUsingApi extends Request<TMDBAPIRequest> {
    
    TMDBAPIRequest toAPIRequest();
}
