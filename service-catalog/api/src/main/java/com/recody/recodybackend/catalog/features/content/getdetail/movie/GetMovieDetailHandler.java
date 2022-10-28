package com.recody.recodybackend.catalog.features.content.getdetail.movie;

import com.recody.recodybackend.catalog.features.PersonalizedMovieDetail;

public interface GetMovieDetailHandler {
    
    PersonalizedMovieDetail handle(GetMovieDetail command);

}
