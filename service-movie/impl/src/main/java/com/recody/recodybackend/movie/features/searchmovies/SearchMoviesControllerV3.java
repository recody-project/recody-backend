package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.common.contents.GenreIds;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.movie.Movies;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Slf4j
@Validated
public class SearchMoviesControllerV3 {
    
    private final SearchMoviesHandlerV2<Movies> searchMoviesHandlerV2;
    private final MessageSource ms;
    
    public SearchMoviesControllerV3(@Qualifier("pagedSearchMoviesHandler") SearchMoviesHandlerV2<Movies> searchMoviesHandlerV2,
                                    MessageSource ms) {
        this.searchMoviesHandlerV2 = searchMoviesHandlerV2;
        this.ms = ms;
    }
    
    @GetMapping( "/api/v3/movie/search" )
    public ResponseEntity<SuccessResponseBody> search2(@RequestParam String movieName,
                                                       HttpServletRequest httpServletRequest,
                                                       @RequestParam( required = false ) List<String> genreIds,
                                                       @RequestParam( defaultValue = "1" ) @Min( value = 1 ) Integer page) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "movie.search.succeeded", null,
                                                            httpServletRequest.getLocale() ) )
                                   .data( searchMoviesHandlerV2.handle(
                                           SearchMovies.builder()
                                                       .movieName( movieName )
                                                       .language( httpServletRequest.getLocale().getLanguage() )
                                                       .genreIds( GenreIds.of( genreIds ) )
                                                       .page( page )
                                                       .build() ) )
                                   .build() );
    }
    
}
