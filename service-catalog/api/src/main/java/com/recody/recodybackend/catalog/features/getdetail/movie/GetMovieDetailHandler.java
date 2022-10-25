package com.recody.recodybackend.catalog.features.getdetail.movie;

import com.recody.recodybackend.catalog.features.PersonalizedMovieDetail;

public interface GetMovieDetailHandler {
    
    PersonalizedMovieDetail handle(GetMovieDetail command);

}
