package com.recody.recodybackend.catalog.features.content.getdetail;

import com.recody.recodybackend.catalog.PersonalizedContentDetail;
import com.recody.recodybackend.catalog.PersonalizedMovieDetail;
import com.recody.recodybackend.catalog.features.content.getdetail.movie.GetMovieDetail;
import com.recody.recodybackend.catalog.features.content.getdetail.movie.GetMovieDetailHandler;
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
class DefaultGetContentDetailHandler implements GetContentDetailHandler {
    private final GetMovieDetailHandler getMovieDetailHandler;
    
    @Override
    public PersonalizedContentDetail handle(GetContentDetail command) {
        log.debug("handling command: {}", command);
        Integer contentId = command.getMovieId();
        String language = command.getLanguage();
        BasicCategory category = command.getCategory();
        
        if (category.equals(BasicCategory.Movie)) {
            PersonalizedMovieDetail detail = getMovieDetailHandler.handle(
                    GetMovieDetail.builder().movieId( contentId ).language( language ).build() );
            return detail;
        } else {
            throw new ApplicationException(GlobalErrorType.UnsupportedCategory, HttpStatus.BAD_REQUEST);
        }
    }
}
