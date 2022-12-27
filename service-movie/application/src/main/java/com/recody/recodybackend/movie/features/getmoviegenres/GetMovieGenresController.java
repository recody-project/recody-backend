package com.recody.recodybackend.movie.features.getmoviegenres;

import com.recody.recodybackend.movie.MovieGenres;
import com.recody.recodybackend.movie.web.MovieHTTPAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class GetMovieGenresController {
    
    private final GetMovieGenresHandler getMovieGenresHandler;
    
    @GetMapping( MovieHTTPAPI.getGenres )
    public ResponseEntity<MovieGenres> getGenres() {
        return ResponseEntity.ok( getMovieGenresHandler.handle() );
    }
}
