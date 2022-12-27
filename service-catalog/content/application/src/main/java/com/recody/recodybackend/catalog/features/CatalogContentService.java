package com.recody.recodybackend.catalog.features;

import com.recody.recodybackend.catalog.features.content.getdetail.movie.GetMovieDetail;
import com.recody.recodybackend.catalog.web.GetMovieDetailResponse;

public interface CatalogContentService {
    
    GetMovieDetailResponse getMovieDetail(GetMovieDetail command);
    
}
