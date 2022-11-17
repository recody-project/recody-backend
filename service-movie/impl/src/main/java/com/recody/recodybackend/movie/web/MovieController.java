package com.recody.recodybackend.movie.web;

import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.movie.MovieDetail;
import com.recody.recodybackend.movie.Movies;
import com.recody.recodybackend.movie.features.MovieDetailService;
import com.recody.recodybackend.movie.features.MovieSearchService;
import com.recody.recodybackend.movie.features.getmoviedetail.fromapi.TMDBFetchedMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.fromdb.GetMovieDetail;
import com.recody.recodybackend.movie.features.searchmovies.SearchMovies;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class MovieController {
    
    private final MovieSearchService movieSearchService;
    private final MovieDetailService<TMDBFetchedMovieDetail, GetMovieDetail> movieDetailService;
    private final MessageSource ms;
    
    @GetMapping( "/api/v1/movie/detail" )
    public ResponseEntity<MovieDetail> getMovieInfo(@RequestParam Integer movieId, HttpServletRequest request,
                                                    @RequestParam( defaultValue = "ko" ) String language) {
        return ResponseEntity.ok().body( movieDetailService.getMovieDetail( new GetMovieDetail( movieId, language ) ).getDetail() );
        
    }
    
    @GetMapping( "/api/v2/movie/detail" )
    public ResponseEntity<SuccessResponseBody> getMovieInfoV2(@RequestParam Integer movieId, HttpServletRequest request,
                                                              @RequestParam( defaultValue = "ko" ) String language) {
        return ResponseEntity.ok()
                             .body( SuccessResponseBody.builder()
                                                       .message( ms.getMessage( "movie.get_info.succeeded", null,
                                                                                request.getLocale() ) )
                                                       .data( movieDetailService.fetchMovieDetail(
                                                               GetMovieDetail.builder()
                                                                             .tmdbId( movieId )
                                                                             .language( language )
                                                                             .build() ) )
                                                       .build() );
    }
    
    @GetMapping( "/api/v1/movie/search" )
    public ResponseEntity<SearchMoviesResult> search(@RequestParam String movieName,
                                                     @RequestParam( defaultValue = "ko" ) String language,
                                                     @RequestParam( defaultValue = "1" ) @Min( value = 1) Integer page) {
        log.debug( "controller called" );
        return ResponseEntity.ok(
                movieSearchService.searchMovies(
                        SearchMovies.builder()
                                    .movieName( movieName )
                                    .language( language )
                                    .page( page )
                                    .build() ) );
    }
    
    @GetMapping( "/api/v2/movie/search" )
    public ResponseEntity<SuccessResponseBody> search2(@RequestParam String movieName, HttpServletRequest request,
                                                       @RequestParam( defaultValue = "ko" ) String language,
                                                       @RequestParam( defaultValue = "1" ) @Min( value = 1) Integer page) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "movie.search.succeeded", null, request.getLocale() ) )
                                   .data( movieSearchService.searchMovies(
                                           SearchMovies.builder()
                                                       .movieName( movieName )
                                                       .language( language )
                                                       .page( page )
                                                       .build() ) )
                                   .build() );
    }
    
    @GetMapping( "/api/v3/movie/search" )
    public ResponseEntity<Movies> search3(@RequestParam String movieName,
                                          HttpServletRequest request,
                                          @RequestParam( defaultValue = "ko" ) String language,
                                          @RequestParam( defaultValue = "1" ) Integer page) {
        return ResponseEntity.ok(
                movieSearchService.searchMoviesMix(
                        SearchMovies.builder()
                                    .movieName( movieName )
                                    .language( language )
                                    .page( page )
                                    .build() ) );
    }
    
    @GetMapping( "/api/v1/movie/search-query" )
    public ResponseEntity<SearchMoviesByQueryResult> searchDB(@RequestParam String movieName,
                                                              @RequestParam( defaultValue = "ko" ) String language,
                                                              @RequestParam( defaultValue = "1" ) @Min( value = 1) Integer page) {
        log.debug( "controller called. {}", "/api/v1/movie/search-query" );
        return ResponseEntity.ok(
                movieSearchService.searchMoviesByQuery(
                        SearchMovies.builder()
                                    .movieName( movieName )
                                    .language( language )
                                    .page( page )
                                    .build() ) );
    }
    
    @GetMapping( "/api/v2/movie/search-query" )
    public ResponseEntity<Movies> searchDB2(@RequestParam String movieName,
                                            @RequestParam( defaultValue = "ko" ) String language,
                                            @RequestParam( defaultValue = "1" ) @Min( value = 1) Integer page) {
        log.debug( "controller called. {}", "/api/v2/movie/search-query" );
        return ResponseEntity.ok(
                movieSearchService.searchMoviesByQueryData(
                        SearchMovies.builder()
                                    .movieName( movieName )
                                    .language( language )
                                    .page( page )
                                    .build() ) );
    }
}

