package com.recody.recodybackend.catalog.features.getdetail;

import com.recody.recodybackend.catalog.features.PersonalizedMovieDetail;
import com.recody.recodybackend.catalog.features.getdetail.movie.GetMovieDetail;
import com.recody.recodybackend.catalog.features.getdetail.movie.GetMovieDetailHandler;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.common.exceptions.GlobalErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetContentDetailHandler implements GetContentDetailHandler{
    private final GetMovieDetailHandler getMovieDetailHandler;
    
    @Override
    public GetContentDetailResult handle(GetContentDetail command) {
        log.debug("handling command: {}", command);
        Integer contentId = command.getContentId();
        String language = command.getLanguage();
        BasicCategory category = command.getCategory();
        
        if (category.equals(BasicCategory.Movie)) {
            PersonalizedMovieDetail detail = getMovieDetailHandler.handle(
                    GetMovieDetail.builder().tmdbId(contentId).language(language).build());
            return new GetContentDetailResult(detail);
        } else {
            throw new ApplicationException(GlobalErrorType.UnsupportedCategory, HttpStatus.BAD_REQUEST);
        }
    }
}
