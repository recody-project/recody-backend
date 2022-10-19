package com.recody.recodybackend.movie.features;

import com.recody.recodybackend.movie.Movie;
import com.recody.recodybackend.movie.MovieDetail;
import com.recody.recodybackend.movie.data.movie.MovieDetailMapper;
import com.recody.recodybackend.movie.data.movie.MovieEntity;
import com.recody.recodybackend.movie.data.movie.MovieRepository;
import com.recody.recodybackend.movie.features.getmoviecredit.*;
import com.recody.recodybackend.movie.features.getmoviedetail.GetMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.GetMovieDetailHandler;
import com.recody.recodybackend.movie.features.getmoviedetail.GetMovieDetailResult;
import com.recody.recodybackend.movie.features.manager.MovieManager;
import com.recody.recodybackend.movie.data.movie.MovieMapper;
import com.recody.recodybackend.movie.features.searchmovies.SearchMovies;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesByQueryResult;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesHandler;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesResult;
import com.recody.recodybackend.movie.features.searchmovies.dto.TMDBMovieSearchNode;
import com.recody.recodybackend.movie.web.TMDBSearchedMovie;
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
    
    private final SearchMoviesHandler<TMDBMovieSearchNode> tMDBSearchMoviesHandler;
    
    private final MovieMapper movieMapper;
    
    private final MovieManager movieManager;
    
    private final MovieDetailMapper movieDetailMapper;
    
    private final MovieRepository movieRepository;
    
    private final SearchMoviesHandler<Movie> dbSearchHandler;
    
    
    
    
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
        
        MovieEntity savedMovie = movieManager.register(joinedMovieDetail, Locale.forLanguageTag(language));
        joinedMovieDetail.setContentId(savedMovie.getId());
        log.info("movieId: {}", savedMovie);
        return GetMovieDetailResult.builder()
                                   .detail(joinedMovieDetail)
                                   .requestInfo(command)
                                   .build();
    }
    
    @Override
    public SearchMoviesResult searchMovies(SearchMovies command) {
        Locale locale = Locale.forLanguageTag(command.getLanguage());
        List<TMDBMovieSearchNode> tmdbMovies = tMDBSearchMoviesHandler.handle(command);
        // Manager 저장 -- 결과에서 저장할 수 있는 정보들을 저장한다.
        CompletableFuture<List<MovieEntity>> movieEntitiesFuture =
                movieManager.registerListAsync(tmdbMovies, locale);
        
        
        // event: MovieSearched
        // 컨슘: 받은 tmdbId 로 detail 을 요청하여 자세한 정보를 받아와 저장해둔다.
        
        
        
        
        // 결과 반환을 위한 매핑
        List<TMDBSearchedMovie> movies = movieMapper.toTMDBMovieList(tmdbMovies);
        
        return SearchMoviesResult.builder()
                       .requestedLanguage(locale)
                       .movies(movies)
                                 .total(movies.size())
                                 .build();
    }
    
    @Override
    public SearchMoviesByQueryResult searchMoviesByQuery(SearchMovies command) {
        log.debug("Searching from DB, command: {}", command);
        String movieName = command.getMovieName();
        String language = command.getLanguage();
        Locale locale = Locale.forLanguageTag(language);
        List<MovieEntity> movieEntities = movieRepository.findByTitleLike(movieName, locale);
        List<Movie> movies = movieMapper.toMovieList(movieEntities, locale);
        log.debug("Searched movies: {}", movies.size());
        return SearchMoviesByQueryResult.builder()
                       .requestedLanguage(locale)
                       .movies(movies)
                       .total(movies.size())
                                        .build();
    }
}
