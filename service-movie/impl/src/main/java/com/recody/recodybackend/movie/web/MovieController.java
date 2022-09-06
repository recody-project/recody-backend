package com.recody.recodybackend.movie.web;

import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.movie.features.searchmovies.MovieSearchService;
import com.recody.recodybackend.movie.features.searchmovies.SearchMovie;
import com.recody.recodybackend.movie.features.searchmovies.SearchMovieResponse;
import com.recody.recodybackend.movie.features.searchmovies.request.MovieSearchTemplate;
import com.recody.recodybackend.movie.features.searchmovies.request.TMDBMovieSearchRequestEntity;
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
    
    private final MovieSearchTemplate movieSearchTemplate;
    private final MovieSearchService movieSearchService;
    private final MessageSource ms;
    
    @GetMapping("/api/v1/movie/search")
    public ResponseEntity<String> search(@RequestParam String movieName,
                                         @RequestParam(defaultValue = "ko") String language){
        log.debug("controller called");
        return ResponseEntity.ok().body(movieSearchTemplate.execute(TMDBMovieSearchRequestEntity
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
    
    private SearchMovieResponse getSearchMovieResponse(String movieName, HttpServletRequest request, String language) {
        return movieSearchService.handle(SearchMovie.builder()
                                                   .movieName(movieName)
                                                   .language(language)
                                                   .build());
    }
}
