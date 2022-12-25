package com.recody.recodybackend.movie.web;

import com.recody.recodybackend.common.contents.GenreIds;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.movie.MovieDetailViewModel;
import com.recody.recodybackend.movie.MovieGenres;
import com.recody.recodybackend.movie.Movies;
import com.recody.recodybackend.movie.features.MovieDetailService;
import com.recody.recodybackend.movie.features.MovieSearchService;
import com.recody.recodybackend.movie.features.getmoviedetail.fromapi.FetchedMovieDetailViewModel;
import com.recody.recodybackend.movie.features.getmoviedetail.fromdb.GetMovieDetail;
import com.recody.recodybackend.movie.features.getmoviegenres.GetMovieGenresHandler;
import com.recody.recodybackend.movie.features.searchmovies.SearchMovies;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesHandlerV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Slf4j
@Validated
public class MovieController {
    
    private final MovieSearchService movieSearchService;
    private final MovieDetailService<FetchedMovieDetailViewModel, GetMovieDetail> movieDetailService;
    private final MessageSource ms;
    
    private final GetMovieGenresHandler getMovieGenresHandler;
    
    private final SearchMoviesHandlerV2<Movies> searchMoviesHandlerV2;
    
    public MovieController(MovieSearchService movieSearchService,
                           MovieDetailService<FetchedMovieDetailViewModel, GetMovieDetail> movieDetailService,
                           MessageSource ms, GetMovieGenresHandler getMovieGenresHandler,
                           @Qualifier("pagedSearchMoviesHandler") SearchMoviesHandlerV2<Movies> searchMoviesHandlerV2) {
        this.movieSearchService = movieSearchService;
        this.movieDetailService = movieDetailService;
        this.ms = ms;
        this.getMovieGenresHandler = getMovieGenresHandler;
        this.searchMoviesHandlerV2 = searchMoviesHandlerV2;
    }
    
    @GetMapping( "/api/v1/movie/detail" )
    public ResponseEntity<MovieDetailViewModel> getMovieInfo(@RequestParam Integer movieId,
                                                             HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok()
                             .body( movieDetailService.getMovieDetail(
                                     new GetMovieDetail( movieId,
                                                         httpServletRequest.getLocale().getLanguage() ) ) );
        
    }
    
    @GetMapping( "/api/v2/movie/detail" )
    @ResponseStatus( HttpStatus.OK )
    public SuccessResponseBody getMovieInfoV2(@RequestParam Integer movieId,
                                              HttpServletRequest httpServletRequest) {
        return SuccessResponseBody.builder()
                                  .message( ms.getMessage( "movie.get_info.succeeded",
                                                           null,
                                                           httpServletRequest.getLocale() ) )
                                  .data( movieDetailService.fetchMovieDetail(
                                          GetMovieDetail.builder()
                                                        .tmdbId( movieId )
                                                        .language(
                                                                httpServletRequest.getLocale().getLanguage() )
                                                        .build() ) )
                                  .build();
    }
    
    @GetMapping( "/api/v1/movie/search" )
    public ResponseEntity<SearchMoviesResult> search(@RequestParam String movieName,
                                                     @RequestParam( defaultValue = "1" ) @Min( value = 1 ) Integer page,
                                                     HttpServletRequest httpServletRequest) {
        log.debug( "controller called" );
        return ResponseEntity.ok(
                movieSearchService.searchMovies(
                        SearchMovies.builder()
                                    .movieName( movieName )
                                    .language( httpServletRequest.getLocale().getLanguage() )
                                    .page( page )
                                    .build() ) );
    }
    
    @GetMapping( "/api/v2/movie/search" )
    public ResponseEntity<SuccessResponseBody> search2(@RequestParam String movieName,
                                                       HttpServletRequest httpServletRequest,
                                                       @RequestParam( defaultValue = "1" ) @Min( value = 1 ) Integer page) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "movie.search.succeeded", null,
                                                            httpServletRequest.getLocale() ) )
                                   .data( movieSearchService.searchMovies(
                                           SearchMovies.builder()
                                                       .movieName( movieName )
                                                       .language(
                                                               httpServletRequest.getLocale().getLanguage() )
                                                       .page( page )
                                                       .build() ) )
                                   .build() );
    }
    
    @GetMapping( "/api/v1/movie/search-query" )
    public ResponseEntity<Movies> searchDB(@RequestParam String movieName,
                                                              @RequestParam( defaultValue = "1" ) @Min( value = 1 ) Integer page,
                                                              @RequestParam( required = false ) List<String> genreIds,
                                                              HttpServletRequest httpServletRequest) {
        log.debug( "controller called. {}", "/api/v1/movie/search-query" );
        return ResponseEntity.ok(
                searchMoviesHandlerV2.handle(
                SearchMovies.builder()
                            .movieName( movieName )
                            .language( httpServletRequest.getLocale().getLanguage() )
                            .genreIds( GenreIds.of( genreIds ) )
                            .page( page )
                            .build() ) );
    }
    
    @GetMapping( "/api/v2/movie/search-query" )
    public ResponseEntity<Movies> searchDB2(@RequestParam String movieName,
                                            @RequestParam( defaultValue = "1" ) @Min( value = 1 ) Integer page,
                                            HttpServletRequest httpServletRequest,
                                            @RequestParam( required = false ) List<String> genreIds) {
        log.debug( "controller called. {}", "/api/v2/movie/search-query" );
        return ResponseEntity.ok(
                searchMoviesHandlerV2.handle(
                        SearchMovies.builder()
                                    .movieName( movieName )
                                    .language( httpServletRequest.getLocale().getLanguage() )
                                    .genreIds( GenreIds.of( genreIds ) )
                                    .page( page )
                                    .build() ) );
    }
    
    @GetMapping( MovieHTTPAPI.getGenres )
    public ResponseEntity<MovieGenres> getGenres() {
        return ResponseEntity.ok( getMovieGenresHandler.handle() );
    }
}