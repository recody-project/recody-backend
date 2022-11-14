package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.catalog.features.genre.addcustomgenre.AddCustomGenre;
import com.recody.recodybackend.catalog.features.genre.addcustomgenre.AddCustomGenreHandler;
import com.recody.recodybackend.category.CategoryId;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.commonbootutils.web.AccessToken;
import com.recody.recodybackend.genre.CustomGenreIconUrl;
import com.recody.recodybackend.genre.CustomGenreName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GenreController {
    
    private final JwtManager jwtManager;
    private final MessageSource ms;
    
    private final AddCustomGenreHandler addCustomGenreHandler;
    
    @PostMapping( "/api/v1/catalog/genre" )
    public ResponseEntity<SuccessResponseBody> addGenre(@RequestBody AddCustomGenreRequest request,
                                                        @AccessToken String accessToken,
                                                        HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.genre.add.succeeded", null, httpServletRequest.getLocale() ) )
                                   .data( AddCustomGenreResponse
                                                  .builder()
                                                  .genre( addCustomGenreHandler.handle(
                                                          AddCustomGenre.builder()
                                                                        .genreName( CustomGenreName.of( request.getGenreName() ) )
                                                                        .genreIconUrl( CustomGenreIconUrl.of( request.getGenreIconUrl() ) )
                                                                        .categoryId( CategoryId.of( request.getCategoryId() ) )
                                                                        .userId( jwtManager.resolveUserId( accessToken ) )
                                                                        .build() ) )
                                                  .build() )
                                   .build()
                                );
    }
    
    
}
