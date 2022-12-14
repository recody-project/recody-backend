package com.recody.recodybackend.catalog.features.getmoviedetail;

import com.recody.recodybackend.catalog.features.fetchmoviedetail.FetchMovieDetail;
import com.recody.recodybackend.catalog.features.fetchmoviedetail.FetchMovieDetailHandler;
import com.recody.recodybackend.catalog.features.personalize.ContentDetailPersonalizer;
import com.recody.recodybackend.common.contents.register.AsyncContentRegistrar;
import com.recody.recodybackend.content.CatalogMovieDetail;
import com.recody.recodybackend.content.PersonalizedMovieDetail;
import com.recody.recodybackend.movie.MovieDetailViewModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;

@Component(value = "CatalogGetMovieDetailHandler")
@RequiredArgsConstructor
@Slf4j
class DefaultGetMovieDetailHandler implements GetMovieDetailHandler<PersonalizedMovieDetail> {
    private final FetchMovieDetailHandler fetchMovieDetailHandler;
    private final ContentDetailPersonalizer<CatalogMovieDetail, PersonalizedMovieDetail> movieDetailPersonalizer;
    private final AsyncContentRegistrar<CatalogMovieDetail, MovieDetailViewModel> movieRegistrar;
    
    @Override
    public PersonalizedMovieDetail handle(GetMovieDetail command) {
        log.debug("handling command: {}", command);
        PersonalizedMovieDetail personalizedMovieDetail;
        Integer contentId = command.getMovieId();
        String language = command.getLanguage();
        Locale locale = Locale.forLanguageTag(language);
        
        MovieDetailViewModel movieDetail
                = fetchMovieDetailHandler.handle(fetchMovieDetailCommand(contentId, language));
        if ( Objects.isNull( movieDetail ) ) {
            return null;
        }
        CatalogMovieDetail catalogMovieDetail
                = movieRegistrar.register(movieDetail, locale);
        personalizedMovieDetail
                = movieDetailPersonalizer.personalize(catalogMovieDetail, command.getUserId());
        return personalizedMovieDetail;
    }
    
    private static FetchMovieDetail fetchMovieDetailCommand(Integer contentId, String language) {
        return FetchMovieDetail.builder().movieId(contentId).language(language).build();
    }
}
