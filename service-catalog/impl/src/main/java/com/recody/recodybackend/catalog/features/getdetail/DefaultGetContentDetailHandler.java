package com.recody.recodybackend.catalog.features.getdetail;

import com.recody.recodybackend.catalog.PersonalizedContentDetail;
import com.recody.recodybackend.catalog.PersonalizedMovieDetail;
import com.recody.recodybackend.catalog.features.getdetail.movie.FetchMovieDetail;
import com.recody.recodybackend.catalog.features.getdetail.movie.FetchMovieDetailHandler;
import com.recody.recodybackend.catalog.features.personalize.ContentDetailPersonalizer;
import com.recody.recodybackend.catalog.features.manager.ContentManager;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.common.exceptions.GlobalErrorType;
import com.recody.recodybackend.movie.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetContentDetailHandler implements GetContentDetailHandler{
    
    private final FetchMovieDetailHandler fetchMovieDetailHandler;
    private final ContentDetailPersonalizer<Movie, PersonalizedMovieDetail> movieDetailPersonalizer;
    private final ContentManager contentManager;
    
    @Override
    public GetContentDetailResult handle(GetContentDetail command) {
        log.debug("handling command: {}", command);
        BasicCategory category = command.getCategory();
        PersonalizedContentDetail personalizedContent;
        if (category.equals(BasicCategory.Movie)) {
            Movie movie = fetchMovieDetailHandler.handle(
                    FetchMovieDetail.builder()
                                    .movieId(command.getContentId())
                                    .language(command.getLanguage())
                                    .build());
            String catalogContentId = contentManager.register(movie);
            personalizedContent
                    = movieDetailPersonalizer.personalize(movie, command.getUserId());
        } else {
            throw new ApplicationException(GlobalErrorType.UnsupportedCategory, HttpStatus.BAD_REQUEST);
        }
        return new GetContentDetailResult(personalizedContent);
    }
}
