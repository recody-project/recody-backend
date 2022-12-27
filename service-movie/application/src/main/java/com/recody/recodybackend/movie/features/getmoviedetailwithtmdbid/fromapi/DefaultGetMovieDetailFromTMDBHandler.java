package com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.fromapi;

import com.recody.recodybackend.movie.Actor;
import com.recody.recodybackend.movie.Director;
import com.recody.recodybackend.movie.data.movie.MovieDetailMapper;
import com.recody.recodybackend.movie.data.people.MoviePersonMapper;
import com.recody.recodybackend.movie.features.applicationevent.MovieDetailFetched;
import com.recody.recodybackend.movie.features.fetchmoviecredit.FetchMovieCreditsHandler;
import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBCast;
import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBCrew;
import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBGetMovieCreditResponse;
import com.recody.recodybackend.movie.features.fetchmoviedetail.FetchMovieDetailHandler;
import com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.fromdb.GetMovieDetailWithTMDBId;
import com.recody.recodybackend.movie.features.tmdb.TMDBMovieID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetMovieDetailFromTMDBHandler implements GetMovieDetailFromTMDBHandler {
    private final MovieDetailMapper movieDetailMapper;
    private final MoviePersonMapper personMapper;
    private final FetchMovieDetailHandler<TMDBMovieDetail, GetMovieDetailWithTMDBId> fetchMovieDetailHandler;
    private final FetchMovieCreditsHandler<TMDBGetMovieCreditResponse, TMDBMovieID> fetchMovieCreditsHandler;
    private final ApplicationEventPublisher applicationEventPublisher;
    
    @Override
    public TMDBFetchedMovieDetail handle(GetMovieDetailWithTMDBId command) {
        log.debug( "handling command: {}", command );
        Integer tmdbId = command.getTmdbId();
        Locale locale = Locale.forLanguageTag( command.getLanguage() );
        TMDBMovieID tmdbMovieID = TMDBMovieID.of( tmdbId );
    
        TMDBMovieDetail detail = fetchMovieDetailHandler.handleAsync( command ).join();
    
        TMDBGetMovieCreditResponse creditResponse = fetchMovieCreditsHandler.handleAsync( tmdbMovieID )
                                                                            .join();
    
        List<TMDBCast> cast = creditResponse.getCast();
        List<TMDBCrew> crew = creditResponse.getCrew();
    
        applicationEventPublisher.publishEvent( MovieDetailFetched.builder()
                                                                  .tmdbMovieDetail( detail )
                                                                  .locale( locale )
                                                                  .casts( cast )
                                                                  .crews( crew )
                                                                  .build() );
    
        TMDBFetchedMovieDetail fetchedMovieDetail = movieDetailMapper.toFetchedMovieDetail( detail );
    
        List<Director> directors = personMapper.toDirector( crew );
        List<Actor> actors = personMapper.toActor( cast );
    
        fetchedMovieDetail.setDirectors( directors );
        fetchedMovieDetail.setActors( actors );
    
        return fetchedMovieDetail;
    }
}
