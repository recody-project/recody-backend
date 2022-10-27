package com.recody.recodybackend.movie.web;

import com.recody.recodybackend.movie.MovieDetail;
import com.recody.recodybackend.movie.Movies;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.movie.features.MovieDetailService;
import com.recody.recodybackend.movie.features.MovieSearchService;
import com.recody.recodybackend.movie.features.getmoviedetail.fromapi.TMDBFetchedMovieDetail;
import com.recody.recodybackend.movie.features.getmoviedetail.fromdb.GetMovieDetail;
import com.recody.recodybackend.movie.features.searchmovies.SearchMovies;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesByQueryResult;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MovieController {
    private final MovieSearchService movieSearchService;
    private final MovieDetailService<TMDBFetchedMovieDetail, GetMovieDetail> movieDetailService;
    private final MessageSource ms;
    
    @GetMapping("/api/v1/movie/detail")
    public ResponseEntity<MovieDetail> getMovieInfo(@RequestParam Integer movieId, HttpServletRequest request,
                                                    @RequestParam(defaultValue = "ko") String language) {
        return ResponseEntity.ok().body(movieDetailService.getMovieDetail(new GetMovieDetail(movieId, language)).getDetail());
        
    }
    
    @GetMapping("/api/v2/movie/detail")
    public ResponseEntity<SuccessResponseBody> getMovieInfoV2(@RequestParam Integer movieId, HttpServletRequest request,
                                                              @RequestParam(defaultValue = "ko") String language) {
        return ResponseEntity.ok()
                             .body(SuccessResponseBody.builder()
                                                      .message(ms.getMessage("movie.get_info.succeeded", null,
                                                                             request.getLocale()))
                                                      .data(movieDetailService.fetchMovieDetail(
                                                              GetMovieDetail.builder()
                                                                                  .tmdbId(movieId)
                                                                                  .language(language)
                                                                                  .build()))
                                                      .build());
    }
    
    @GetMapping("/api/v1/movie/search")
    public ResponseEntity<SearchMoviesResult> search(@RequestParam String movieName,
                                                     @RequestParam(defaultValue = "ko") String language) {
        log.debug("controller called");
        return ResponseEntity.ok()
                             .body(movieSearchService.searchMovies(
                                     SearchMovies.builder().movieName(movieName).language(language).build()));
    }
    
    @GetMapping("/api/v1/movie/search-query")
    public ResponseEntity<SearchMoviesByQueryResult> searchDB(@RequestParam String movieName,
                                                              @RequestParam(defaultValue = "ko") String language) {
        log.debug("controller called. {}", "/api/v1/movie/search-query");
        return ResponseEntity.ok()
                             .body(movieSearchService.searchMoviesByQuery(
                                     SearchMovies.builder().movieName(movieName).language(language).build()));
    }
    
    @GetMapping("/api/v2/movie/search-query")
    public ResponseEntity<Movies> searchDBData(@RequestParam String movieName,
                                               @RequestParam(defaultValue = "ko") String language) {
        log.debug("controller called. {}", "/api/v2/movie/search-query");
        return ResponseEntity.ok()
                             .body(movieSearchService.searchMoviesByQueryData(
                                     SearchMovies.builder().movieName(movieName).language(language).build()));
    }
    
    @GetMapping("/api/v2/movie/search")
    public ResponseEntity<SuccessResponseBody> searchV2(@RequestParam String movieName, HttpServletRequest request,
                                                        @RequestParam(defaultValue = "ko") String language) {
        return ResponseEntity.ok()
                             .body(SuccessResponseBody.builder()
                                                      .message(ms.getMessage("movie.search.succeeded", null,
                                                                             request.getLocale()))
                                                      .data(getSearchMovieResponse(movieName, language))
                                                      .build());
    }
    
    private SearchMoviesResult getSearchMovieResponse(String movieName, String language) {
        return movieSearchService.searchMovies(SearchMovies.builder().movieName(movieName).language(language).build());
    }
}

