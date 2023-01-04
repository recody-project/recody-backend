package com.recody.recodybackend.catalog.features.getmoviedetail;

import com.recody.recodybackend.catalog.web.GetContentDetailResponse;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.commonbootutils.web.AccessToken;
import com.recody.recodybackend.content.PersonalizedMovieDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
class GetMovieDetailController {
    
    
    private final MessageSource ms;
    private final JwtManager jwtManager;
    private final GetMovieDetailHandler<PersonalizedMovieDetail> getMovieDetailHandler;
    private final GetMovieDetailFromMovieDBHandler<PersonalizedMovieDetail> getMovieDetailFromMovieDBHandler;
    
    @GetMapping( "/api/v1/catalog/movie/{tmdbId}" )
    public ResponseEntity<SuccessResponseBody> movieDetail(@PathVariable Integer tmdbId,
                                                           @Nullable @RequestParam( defaultValue = "ko" ) String language,
                                                           @AccessToken String accessToken, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.content.movie.detail.get.succeeded", null,
                                                            httpServletRequest.getLocale() ) )
                                   .data( new GetContentDetailResponse(
                                           getMovieDetailHandler.handle( GetMovieDetail.builder()
                                                                                       .movieId( tmdbId )
                                                                                       .language( language )
                                                                                       .userId( jwtManager.resolveUserId( accessToken ) )
                                                                                       .build() ) ) )
                                   .build() );
    }
    
    @GetMapping( "/api/v2/catalog/movie/{movieId}" )
    public ResponseEntity<SuccessResponseBody> movieDetail(@PathVariable String movieId,
                                                           @AccessToken String accessToken, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.content.movie.detail.get.succeeded", null,
                                                            httpServletRequest.getLocale() ) )
                                   .data( new GetContentDetailResponse(
                                           getMovieDetailFromMovieDBHandler.handle( GetMovieDetailFromMovieDB.builder()
                                                                                       .movieId( movieId )
                                                                                       .locale( httpServletRequest.getLocale() )
                                                                                       .userId( jwtManager.resolveUserId( accessToken ) )
                                                                                       .build() ) ) )
                                   .build() );
    }
}
