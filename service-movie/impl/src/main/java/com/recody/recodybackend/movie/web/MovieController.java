package com.recody.recodybackend.movie.web;

import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.movie.features.getmovieDetail.GetMovieDetailHandler;
import com.recody.recodybackend.movie.features.getmovieDetail.TMDBGetMovieDetail;
import com.recody.recodybackend.movie.features.searchmovies.MovieSearchService;
import com.recody.recodybackend.movie.features.searchmovies.SearchMovies;
import com.recody.recodybackend.movie.features.searchmovies.SearchMoviesResult;
import com.recody.recodybackend.movie.features.searchmovies.request.SearchMoviesUsingApiHandler;
import com.recody.recodybackend.movie.features.searchmovies.request.SearchMoviesUsingTMDBApi;
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
    
    private final SearchMoviesUsingApiHandler searchMoviesUsingApiHandler;
    private final GetMovieDetailHandler getMovieDetailHandler;
    private final MovieSearchService movieSearchService;
    private final MessageSource ms;
    
    @GetMapping("/api/v1/movie/search")
    public ResponseEntity<String> search(@RequestParam String movieName,
                                         @RequestParam(defaultValue = "ko") String language){
        log.debug("controller called");
        return ResponseEntity.ok().body(searchMoviesUsingApiHandler.handleToString(SearchMoviesUsingTMDBApi
                                                                            .builder()
                                                                            .movieName(movieName)
                                                                            .language(language)
                                                                            .build()));
    }
    
    @GetMapping("/api/v2/movie/search")
    public ResponseEntity<SuccessResponseBody> searchV2(@RequestParam String movieName,
                                                        HttpServletRequest request,
                                                        @RequestParam(defaultValue = "ko") String language) {
        return ResponseEntity.ok().body(SuccessResponseBody.builder()
                                                           .message(ms.getMessage("movie.search.succeeded", null, request.getLocale()))
                                                           .data(getSearchMovieResponse(movieName, request, language)).build());
    }
    
    private SearchMoviesResult getSearchMovieResponse(String movieName, HttpServletRequest request, String language) {
        return movieSearchService.handle(SearchMovies.builder()
                                                     .movieName(movieName)
                                                     .language(language)
                                                     .build());
    }
    
    @GetMapping("/api/v1/movie/info")
    public ResponseEntity<String> getMovieInfo(@RequestParam String movieId,
                                                            HttpServletRequest request,
                                                            @RequestParam(defaultValue = "ko") String language) {
        return ResponseEntity.ok()
                             .body(getMovieDetailHandler.handleToString(new TMDBGetMovieDetail(movieId, language)));
        
    }
    
    @GetMapping("/api/v2/movie/info")
    public ResponseEntity<SuccessResponseBody> getMovieInfoV2(@RequestParam String movieId,
                                                              HttpServletRequest request,
                                                              @RequestParam(defaultValue = "ko") String language) {
        return ResponseEntity.ok().body(SuccessResponseBody.builder()
                                                           .message(ms.getMessage("movie.get_info.succeeded", null, request.getLocale()))
                                                           .data(getMovieDetailHandler.handleToResponse(new TMDBGetMovieDetail(movieId, language)))
                                                           .build());
    }
}

