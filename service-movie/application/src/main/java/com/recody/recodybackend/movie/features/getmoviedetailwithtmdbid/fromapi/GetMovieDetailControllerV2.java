package com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.fromapi;

import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.movie.features.getmoviedetailwithtmdbid.fromdb.GetMovieDetailWithTMDBId;
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
public class GetMovieDetailControllerV2 {
    private final GetMovieDetailFromTMDBHandler getMovieDetailFromTMDBHandler;
    private final MessageSource ms;
    
    
    @GetMapping( "/api/v2/movie/detail" )
    @ResponseStatus( HttpStatus.OK )
    public SuccessResponseBody getMovieInfoV2(@RequestParam Integer movieId,
                                              HttpServletRequest httpServletRequest) {
        return SuccessResponseBody.builder()
                                  .message( ms.getMessage( "movie.get_info.succeeded",
                                                           null,
                                                           httpServletRequest.getLocale() ) )
                                  .data( getMovieDetailFromTMDBHandler.handle(
                                          GetMovieDetailWithTMDBId.builder()
                                                                  .tmdbId( movieId )
                                                                  .language( httpServletRequest.getLocale().getLanguage() )
                                                                  .build() ) )
                                  .build();
    }
}
