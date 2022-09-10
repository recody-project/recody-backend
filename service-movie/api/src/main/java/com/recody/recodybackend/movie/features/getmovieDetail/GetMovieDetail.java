package com.recody.recodybackend.movie.features.getmovieDetail;

import com.recody.recodybackend.common.openapi.Request;
import com.recody.recodybackend.movie.general.TMDBRequestEntity;

public interface GetMovieDetail extends Request<TMDBRequestEntity> {
    
    @Override
    TMDBRequestEntity toEntity();
    
    String getLanguage();
}
