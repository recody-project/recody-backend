package com.recody.recodybackend.movie.features;

import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.MovieDetail;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieEntityMapper;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.features.getmoviecredit.*;
import com.recody.recodybackend.movie.features.getmoviedetail.GetMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.GetMovieDetailHandler;
import com.recody.recodybackend.movie.features.getmoviedetail.GetMovieDetailResult;
import com.recody.recodybackend.movie.features.manager.MovieManager;
import com.recody.recodybackend.movie.features.searchmovies.SearchMovies;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesHandler;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesResult;
import com.recody.recodybackend.movie.features.searchmovies.TMDBMovieSearchMapper;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultMovieService implements MovieService {
    
    private final GetMovieDetailHandler getMovieDetailHandler;
    private final GetMovieCreditHandler getMovieCreditHandler;
    
    private final SearchMoviesHandler<TMDBMovieSearchNode> searchMoviesHandler;
    
    private final TMDBMovieSearchMapper movieSearchMapper;
    
    private final MovieManager movieManager;
    
    private final MovieEntityMapper movieEntityMapper;
    
    private final MovieRepository movieRepository;
    
    
    
    
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
    
    @Override
    public SearchMoviesResult searchMovies(SearchMovies command) {
        List<TMDBMovieSearchNode> tmdbMovies = searchMoviesHandler.handle(command);
        // 저장
        ArrayList<MovieEntity> movieEntities = new ArrayList<>();
        Locale locale = Locale.forLanguageTag(command.getLanguage());
        for (TMDBMovieSearchNode tmdbMovie : tmdbMovies) {
            Optional<MovieEntity> optionalMovie = movieRepository.findByTmdbId(tmdbMovie.getId());
            if (optionalMovie.isEmpty()) {
                MovieEntity movieEntity = movieSearchMapper.mapEntity(tmdbMovie, locale);
                MovieEntity saved = movieRepository.save(movieEntity);
                movieEntities.add(saved);
            } else {
                MovieEntity entity = optionalMovie.get();
                movieEntities.add(entity);
            }
        }
        
        // entity 를 movie 객체로 바꾼다.
        List<Movie> movies = movieEntityMapper.mapMovieList(movieEntities,
                                                            Locale.forLanguageTag(command.getLanguage()));
        
        return SearchMoviesResult.builder()
                       .requestedLanguage(Locale.forLanguageTag(command.getLanguage()))
                       .movies(movies)
                                 .total(movies.size())
                                 .build();
    }
}
