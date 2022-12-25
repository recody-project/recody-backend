package com.recody.recodybackend.movie.features.getmoviedetail.fromdb;

import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.movie.MovieDetailViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class GetMovieDetailControllerV3 {
    
    private final MessageSource ms;
    private final GetMovieDetailHandlerV2<MovieDetailViewModel> getMovieDetailHandlerV2;
    
    
    
    @GetMapping( "/api/v3/movie/detail" )
    @ResponseStatus( HttpStatus.OK )
    public SuccessResponseBody getMovieInfoV2(@RequestParam String movieId,
                                              HttpServletRequest httpServletRequest) {
        return SuccessResponseBody.builder()
                                  .message( ms.getMessage( "movie.get_info.succeeded",
                                                           null,
                                                           httpServletRequest.getLocale() ) )
                                  .data( getMovieDetailHandlerV2.handle(
                                          GetMovieDetailV2.builder()
                                                        .movieId( movieId )
                                                        .locale( httpServletRequest.getLocale() )
                                                        .build() ) )
                                  .build();
    }
}
