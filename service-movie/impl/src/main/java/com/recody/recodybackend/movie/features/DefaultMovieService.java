package com.recody.recodybackend.movie.features;

import com.recody.recodybackend.movie.MovieDetail;
import com.recody.recodybackend.movie.features.getmoviecredit.*;
import com.recody.recodybackend.movie.features.getmoviedetail.GetMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.GetMovieDetailHandler;
import com.recody.recodybackend.movie.features.getmoviedetail.GetMovieDetailResult;
import com.recody.recodybackend.movie.features.manager.MovieManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultMovieService implements MovieService {
    
    private final GetMovieDetailHandler getMovieDetailHandler;
    private final GetMovieCreditHandler getMovieCreditHandler;
    
    private final MovieManager movieManager;
    
    
    @Override
    public GetMovieDetailResult getMovieDetail(GetMovieDetail command) {
        String movieId = command.getMovieId();
        String language = command.getLanguage();
        CompletableFuture<MovieDetail> movieFuture = getMovieDetailHandler.handleAsync(command);
        CompletableFuture<GetMovieCreditResult> creditFuture = getMovieCreditHandler.handleAsync(
                new GetMovieCredit(Long.parseLong(movieId)));
        MovieDetail joinedMovieDetail =
                movieFuture.thenCombine(creditFuture, ((movie, credits) -> {
                    List<Actor> actors = credits.getActors();
                    List<Director> directors = credits.getDirectors();
                    movie.setActors(actors);
                    movie.setDirectors(directors);
                    log.info("Movie 에 actor 와 director 를 세팅하였습니다.");
                    return movie;
                })).join();
        
        String savedMovieId = movieManager.register(joinedMovieDetail, Locale.forLanguageTag(language));
        joinedMovieDetail.setContentId(savedMovieId);
        log.info("movieId: {}", savedMovieId);
        return GetMovieDetailResult.builder()
                                   .detail(joinedMovieDetail)
                                   .requestInfo(command)
                                   .build();
    }
}
