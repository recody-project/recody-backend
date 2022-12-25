package com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.fromdb;

import com.recody.recodybackend.movie.MovieDetailViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class GetMovieDetailControllerV1 {
    
    private final GetMovieDetailWithTMDBIdHandler<MovieDetailViewModel> getMovieDetailWithTMDBIdHandler;
    
    
    
    @GetMapping( "/api/v1/movie/detail" )
    public ResponseEntity<MovieDetailViewModel> getMovieInfo(@RequestParam Integer movieId,
                                                             HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok()
                             .body( getMovieDetailWithTMDBIdHandler.handle(
                                     GetMovieDetailWithTMDBId.builder()
                                                             .tmdbId( movieId )
                                                             .language( httpServletRequest.getLocale().getLanguage() )
                                                             .build() ) );
        
    }
}
