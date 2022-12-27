package com.recody.recodybackend.catalog.features.content.getdetail.movie;

import com.recody.recodybackend.content.PersonalizedMovieDetail;

public interface GetMovieDetailHandler {
    
    PersonalizedMovieDetail handle(GetMovieDetail command);

}
