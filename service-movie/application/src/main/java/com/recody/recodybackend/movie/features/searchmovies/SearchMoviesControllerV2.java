package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.movie.web.SearchMoviesResult;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;

@RestController
class SearchMoviesControllerV2 {
    
    private final SearchMoviesHandler<SearchMoviesResult> searchMoviesHandler;
    private final MessageSource ms;
    
    public SearchMoviesControllerV2(
            @Qualifier( "tmdbSearchMoviesHandler" ) SearchMoviesHandler<SearchMoviesResult> searchMoviesHandler,
            MessageSource ms) {
        this.searchMoviesHandler = searchMoviesHandler;
        this.ms = ms;
    }
    
    @GetMapping( "/api/v2/movie/search" )
    public ResponseEntity<SuccessResponseBody> search2(@RequestParam String movieName,
                                                       HttpServletRequest httpServletRequest,
                                                       @RequestParam( defaultValue = "1" ) @Min( value = 1 ) Integer page) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "movie.search.succeeded", null,
                                                            httpServletRequest.getLocale() ) )
                                   .data( searchMoviesHandler.handle(
                                           SearchMovies.builder()
                                                       .movieName( movieName )
                                                       .language( httpServletRequest.getLocale().getLanguage() )
                                                       .page( page )
                                                       .build() ) )
                                   .build() );
    }
}
