package com.recordy.recordybackend.movie.web;

import com.recordy.recordybackend.movie.features.searchmovies.MovieSearchTemplate;
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
    public ResponseEntity<String> searchTmdb(@RequestParam String movieName){
        log.info("controller called");
        return ResponseEntity.ok().body(movieSearchTemplate.movieName(movieName).korean().execute());
    }
}
