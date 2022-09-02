package com.recody.recodybackend.movie.web;

import com.recody.recodybackend.movie.features.searchmovies.request.TMDBMovieSearchRequest;
import com.recody.recodybackend.movie.features.searchmovies.MovieSearchTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MovieController {
    
    private final MovieSearchTemplate movieSearchTemplate;
    
    @GetMapping("/api/v1/movie/search")
    public ResponseEntity<String> search(@RequestParam String movieName){
        log.debug("controller called");
        return ResponseEntity.ok().body(movieSearchTemplate.execute(TMDBMovieSearchRequest
                                                                            .builder()
                                                                            .movieName(movieName)
                                                                            .korean()
                                                                            .build()));
    }
}
