package com.recody.recodybackend.movie.features.synchronizemoviedetail;

import com.recody.recodybackend.movie.MovieInfo;
import com.recody.recodybackend.movie.features.fetchmoviecredit.FetchMovieCreditsHandler;
import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBGetMovieCreditResponse;
import com.recody.recodybackend.movie.features.fetchmoviedetail.FetchMovieDetailHandler;
import com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.fromdb.GetMovieDetailWithTMDBId;
import com.recody.recodybackend.movie.features.manager.MovieManager;
import com.recody.recodybackend.movie.features.tmdb.TMDBMovieID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSynchronizeMovieDetailHandler implements SynchronizeMovieDetailHandler<Void> {
    
    private static final List<Locale> locales = List.of( Locale.KOREAN, Locale.ENGLISH );
    
    private final FetchMovieDetailHandler<TMDBMovieDetail, GetMovieDetailWithTMDBId> fetchMovieDetailHandler;
    
    private final FetchMovieCreditsHandler<TMDBGetMovieCreditResponse, TMDBMovieID> fetchMovieCreditsHandler;
    
    private final MovieManager movieManager;
    
    @Override
    @Transactional
    public Void handle(SynchronizeMovieDetail command) {
        
        log.debug( "handling command: {}", command );
        Integer movieId = command.getTmdbId();
        
        for (Locale locale : locales) {
            MovieInfo registeredMovie =
                    fetchMovieDetailHandler.handleAsync( new GetMovieDetailWithTMDBId( movieId, locale.getLanguage() ) )
                                           .thenApply( tmdbMovieDetail -> movieManager.register( tmdbMovieDetail,
                                                                                             locale ) ).join();
            log.info( "registeredMovie: {}", registeredMovie );
            if (locale.equals( Locale.ENGLISH )) {
                fetchMovieCreditsHandler.handleAsync( TMDBMovieID.of( movieId ) )
                                        .thenApply( response -> {
                                            movieManager.actor()
                                                        .registerAsync( registeredMovie, response.getCast(),
                                                                        Locale.ENGLISH );
                                            movieManager.director()
                                                        .registerAsync( registeredMovie, response.getCrew(),
                                                                        Locale.ENGLISH );
                                            return null;
                                        } );
            }
        }
        return null;
    }
}
