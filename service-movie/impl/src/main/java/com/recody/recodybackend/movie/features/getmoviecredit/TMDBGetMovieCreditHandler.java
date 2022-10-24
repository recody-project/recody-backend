package com.recody.recodybackend.movie.features.getmoviecredit;

import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBCast;
import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBCrew;
import com.recody.recodybackend.movie.features.getmoviecredit.dto.TMDBGetMovieCreditResponse;
import com.recody.recodybackend.movie.features.manager.MovieManager;
import com.recody.recodybackend.movie.features.tmdb.TMDBMovieID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
class TMDBGetMovieCreditHandler implements GetMovieCreditHandler {
    
    private final FetchMovieCreditsHandler<TMDBGetMovieCreditResponse, TMDBMovieID> fetcher;
    private final MovieManager movieManager;
    
    @Override
    public GetMovieCreditResult handle(GetMovieCredit command) {
        Integer movieId = command.getMovieId();
        
        TMDBGetMovieCreditResponse response = fetcher.handle(TMDBMovieID.of(movieId));
        
        List<TMDBCast> cast = response.getCast();
        List<TMDBCrew> crew = response.getCrew();
        
        Movie movie = movieManager.load(TMDBMovieID.of(movieId), Locale.ENGLISH)
                                   .orElseThrow(ContentNotFoundException::new);
        CompletableFuture<List<Actor>> actorsFuture = movieManager.actor()
                                                                  .registerAsync(movie, cast, Locale.ENGLISH);
        CompletableFuture<List<Director>> directorsFuture = movieManager.director()
                                                                        .registerAsync(movie, crew, Locale.ENGLISH);
        return actorsFuture.thenCombine(directorsFuture,(
                ((actors, directors) -> GetMovieCreditResult.builder()
                                                         .actors(actors)
                                                         .directors(directors)
                                                         .build()))
                                       ).join();
    }
    
    @Override
    @Async
    public CompletableFuture<GetMovieCreditResult> handleAsync(GetMovieCredit command){
        return CompletableFuture.completedFuture(this.handle(command));
    }
}
