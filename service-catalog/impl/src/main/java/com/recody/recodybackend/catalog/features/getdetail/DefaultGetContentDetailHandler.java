package com.recody.recodybackend.catalog.features.getdetail;

import com.recody.recodybackend.catalog.PersonalizedContent;
import com.recody.recodybackend.catalog.PersonalizedMovie;
import com.recody.recodybackend.catalog.features.getdetail.movie.FetchMovieDetail;
import com.recody.recodybackend.catalog.features.getdetail.movie.FetchMovieDetailHandler;
import com.recody.recodybackend.catalog.features.personalize.ContentPersonalizer;
import com.recody.recodybackend.catalog.features.manager.ContentManager;
import com.recody.recodybackend.common.contents.Category;
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
    private final ContentPersonalizer<Movie, PersonalizedMovie> movieDetailPersonalizer;
    private final ContentManager contentManager;
    
    @Override
    public GetContentDetailResult handle(GetContentDetail command) {
        log.debug("handling command: {}", command);
        Category category = command.getCategory();
        PersonalizedContent personalizedcontent;
        if (category.equals(Category.Movie)) {
            Movie movie = fetchMovieDetailHandler.handle(
                    FetchMovieDetail.builder()
                                    .movieId(command.getContentId())
                                    .language(command.getLanguage())
                                    .build());
            String catalogContentId = contentManager.register(movie);
            personalizedcontent
                    = movieDetailPersonalizer.personalize(movie, command.getUserId());
        } else {
            throw new ApplicationException(GlobalErrorType.UnsupportedCategory, HttpStatus.BAD_REQUEST);
        }
        return new GetContentDetailResult(personalizedcontent);
    }
}
