package com.recody.recodybackend.catalog.features;

import com.recody.recodybackend.catalog.features.content.getdetail.movie.GetMovieDetail;
import com.recody.recodybackend.catalog.features.content.getdetail.movie.GetMovieDetailHandler;
import com.recody.recodybackend.catalog.web.GetMovieDetailResponse;
import com.recody.recodybackend.content.PersonalizedMovieDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultCatalogContentService implements CatalogContentService{
    
    private final GetMovieDetailHandler getMovieDetailHandler;
    
    @Override
    public GetMovieDetailResponse getMovieDetail(GetMovieDetail command) {
        PersonalizedMovieDetail personalizedMovieDetail = getMovieDetailHandler.handle( command );
        
        return null;
    }
}
