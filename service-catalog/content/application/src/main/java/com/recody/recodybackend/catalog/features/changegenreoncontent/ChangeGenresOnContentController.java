package com.recody.recodybackend.catalog.features.changegenreoncontent;

import com.recody.recodybackend.catalog.web.ChangeGenresOnContentRequest;
import com.recody.recodybackend.catalog.web.ChangeGenresOnContentResponse;
import com.recody.recodybackend.common.contents.ContentId;
import com.recody.recodybackend.common.contents.GenreIds;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.commonbootutils.web.AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
class ChangeGenresOnContentController {
    
    private final ChangeGenresOnContentHandler changeGenresOnContentHandler;
    private final JwtManager jwtManager;
    private final MessageSource ms;
    
    @PatchMapping( "/api/v1/catalog/content/{contentId}/genres" )
    public ResponseEntity<SuccessResponseBody> changeGenreInfo(@AccessToken String accessToken,
                                                               HttpServletRequest httpServletRequest,
                                                               @PathVariable String contentId,
                                                               @RequestBody ChangeGenresOnContentRequest request
                                                              ) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.content.detail.change-genres.succeeded", null,
                                                            httpServletRequest.getLocale() ) )
                                   .data( ChangeGenresOnContentResponse
                                                  .builder()
                                                  .event( changeGenresOnContentHandler.handle(
                                                          ChangeGenresOnContent
                                                                  .builder()
                                                                  .genreIds( new GenreIds( request.getGenreIds() ) )
                                                                  .userId( jwtManager.resolveUserId( accessToken ) )
                                                                  .contentId( ContentId.of( contentId ) )
                                                                  .build() ) ).build()
                                        )
                                   .build() );
    }
}
