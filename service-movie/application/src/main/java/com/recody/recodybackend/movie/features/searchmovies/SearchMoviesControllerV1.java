package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.movie.web.SearchMoviesResult;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;

@RestController
class SearchMoviesControllerV1 {
    
    
    private final SearchMoviesHandler<SearchMoviesResult> searchMoviesHandler;
    
    public SearchMoviesControllerV1(
            @Qualifier( "tmdbSearchMoviesHandler" )
            SearchMoviesHandler<SearchMoviesResult> searchMoviesHandler) {
        this.searchMoviesHandler = searchMoviesHandler;
    }
    
    @GetMapping( {"/api/v1/movie/search"} )
    @ResponseStatus( HttpStatus.OK )
    public SearchMoviesResult search(@RequestParam String movieName,
                          HttpServletRequest httpServletRequest,
                          @RequestParam( defaultValue = "1" ) @Min( value = 1 ) Integer page) {
        return searchMoviesHandler.handle(
                SearchMovies.builder()
                            .movieName( movieName )
                            .language( httpServletRequest.getLocale().getLanguage() )
                            .page( page )
                            .build() );
    }
}
