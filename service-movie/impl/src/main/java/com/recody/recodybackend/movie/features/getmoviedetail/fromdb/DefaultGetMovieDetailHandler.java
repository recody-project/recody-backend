package com.recody.recodybackend.movie.features.getmoviedetail.fromdb;

import com.recody.recodybackend.movie.Actor;
import com.recody.recodybackend.movie.Director;
import com.recody.recodybackend.movie.MovieDetail;
import com.recody.recodybackend.movie.MovieSource;
import com.recody.recodybackend.movie.data.movie.MovieDetailMapper;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.features.fetchmoviecredit.FetchMovieCreditsHandler;
import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBCast;
import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBCrew;
import com.recody.recodybackend.movie.features.fetchmoviecredit.dto.TMDBGetMovieCreditResponse;
import com.recody.recodybackend.movie.MovieInfo;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.TMDBMovieDetail;
import com.recody.recodybackend.movie.features.fetchmoviedetail.FetchMovieDetailHandler;
import com.recody.recodybackend.movie.features.manager.MovieManager;
import com.recody.recodybackend.movie.features.tmdb.TMDBMovieID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
class DefaultGetMovieDetailHandler implements GetMovieDetailHandler {
    
    private final MovieDetailMapper mapper;
    private final MovieRepository movieRepository;
    private final MovieManager movieManager;
    private final FetchMovieDetailHandler<TMDBMovieDetail, GetMovieDetail> fetchMovieDetailHandler;
    private final FetchMovieCreditsHandler<TMDBGetMovieCreditResponse, TMDBMovieID> fetchMovieCreditsHandler;
    
    @Override
    public MovieDetail handle(GetMovieDetail command) {
        log.debug("handling command: {}", command);
        String language = command.getLanguage();
        Integer movieId = command.getTmdbId();
        Locale locale = Locale.forLanguageTag(language);
    
        MovieInfo registeredMovie =
                fetchMovieDetailHandler.handleAsync(new GetMovieDetail(movieId, language))
                                       .thenApply(tmdbMovieDetail -> movieManager.register(tmdbMovieDetail, locale))
                                       .join();
    
        TMDBGetMovieCreditResponse credits =
                fetchMovieCreditsHandler.handleAsync(TMDBMovieID.of(movieId)).join();
        
        log.info("registeredMovie: {}", registeredMovie);
        
        
        List<TMDBCast> cast = credits.getCast();
        CompletableFuture<List<Actor>> actorFuture =
                movieManager.actor()
                            .registerAsync(registeredMovie, cast, Locale.ENGLISH);
        List<TMDBCrew> crew = credits.getCrew();
        CompletableFuture<List<Director>> directorFuture =
                movieManager.director()
                            .registerAsync(registeredMovie, crew, locale);
        
        return CompletableFuture
                       .allOf(actorFuture, directorFuture)
                       .thenApply(ignoredVoid -> {
                                      List<Actor> joinedActors = actorFuture.join();
                                      List<Director> joinedDirectors = directorFuture.join();
                                      MovieDetail movieDetail =
                                              mapper.makeMovieDetail(registeredMovie, joinedActors, joinedDirectors);
                                      log.info("TMDB 에서 영화정보를 가져왔습니다.");
                               return movieDetail;
                       }
                       ).join();
    }
    
    @Override
    @Transactional
    public MovieDetail handleFromDB(GetMovieDetail command) {
        Integer movieId = command.getTmdbId();
        Optional<MovieEntity> optionalMovie = movieRepository.findByTmdbId(movieId);
        if (optionalMovie.isEmpty()) {
            return null;
        }
        
        // 이 movie 는 레코디의 movie. 장르정보는 고유 장르 id 를 가지고 있다.
        MovieDetail movieDetail = mapper.map(optionalMovie.get(), MovieSource.TMDB, Locale.forLanguageTag(command.getLanguage()));
        log.debug("found movieDetail from db: {}", movieDetail);
        return movieDetail;
    }
}
