package com.recody.recodybackend.catalog.features.getmoviedetail;

import com.recody.recodybackend.catalog.data.content.CatalogContentMapper;
import com.recody.recodybackend.catalog.features.fetchmoviedetailv2.FetchMovieDetailHandlerV2;
import com.recody.recodybackend.catalog.features.fetchmoviedetailv2.FetchMovieDetailV2;
import com.recody.recodybackend.catalog.features.personalize.ContentDetailPersonalizer;
import com.recody.recodybackend.content.CatalogMovieDetail;
import com.recody.recodybackend.content.PersonalizedMovieDetail;
import com.recody.recodybackend.movie.MovieDetailViewModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetMovieDetailFromMovieDBHandler
        implements GetMovieDetailFromMovieDBHandler<PersonalizedMovieDetail> {
    
    private final FetchMovieDetailHandlerV2<MovieDetailViewModel> fetchMovieDetailHandler;
    private final ContentDetailPersonalizer<CatalogMovieDetail, PersonalizedMovieDetail>
            movieDetailPersonalizer;
    
    private final CatalogContentMapper catalogContentMapper;
    
    @Override
    public PersonalizedMovieDetail handle(GetMovieDetailFromMovieDB command) {
        log.debug( "handling command: {}", command );
        PersonalizedMovieDetail personalizedMovieDetail;
        String contentId = command.getMovieId();
        Locale locale = command.getLocale();
        
        MovieDetailViewModel movieDetail
                = fetchMovieDetailHandler.handle( new FetchMovieDetailV2( contentId, locale ) );
        if ( Objects.isNull( movieDetail ) ) {
            return null;
        }
        // dto -> catalogMovieDetail
        CatalogMovieDetail catalogMovieDetail = catalogContentMapper.toCatalogMovieDetail( movieDetail,
                                                                                           locale );
        
        personalizedMovieDetail
                = movieDetailPersonalizer.personalize( catalogMovieDetail, command.getUserId() );
        return personalizedMovieDetail;
    }
}
