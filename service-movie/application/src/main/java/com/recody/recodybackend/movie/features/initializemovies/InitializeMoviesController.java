package com.recody.recodybackend.movie.features.initializemovies;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class InitializeMoviesController {
    private final InitializeMoviesHandler<Void> initializeMoviesHandler;

    @GetMapping("/api/v1/movie/initialize")
    public Void initialize(){
        return initializeMoviesHandler.handle();
    }


}
