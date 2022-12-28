package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.catalog.features.changecategoryoncontent.ChangeCategoryOnContent;
import com.recody.recodybackend.catalog.features.changecategoryoncontent.ChangeCategoryOnContentHandler;
import com.recody.recodybackend.catalog.features.changegenreoncontent.ChangeGenresOnContent;
import com.recody.recodybackend.catalog.features.changegenreoncontent.ChangeGenresOnContentHandler;
import com.recody.recodybackend.category.CustomCategoryId;
import com.recody.recodybackend.common.contents.ContentId;
import com.recody.recodybackend.common.contents.GenreIds;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.commonbootutils.web.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
class CatalogController {
    private final ChangeCategoryOnContentHandler changeCategoryOnContentHandler;
    private final ChangeGenresOnContentHandler changeGenresOnContentHandler;
    private final JwtManager jwtManager;
    private final MessageSource ms;
    
    @PatchMapping( "/api/v1/catalog/content/{contentId}/category" )
    public ResponseEntity<SuccessResponseBody> changeContentInfo(@AccessToken String accessToken,
                                                                 HttpServletRequest httpServletRequest,
                                                                 @PathVariable String contentId,
                                                                 @RequestBody SetCustomCategoryRequest request
                                                                ) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.content.detail.change-category.succeeded", null,
                                                            httpServletRequest.getLocale() ) )
                                   .data( ChangeCategoryOnContentResponse
                                                  .builder()
                                                  .event( changeCategoryOnContentHandler.handle(
                                                          ChangeCategoryOnContent
                                                                  .builder()
                                                                  .categoryId( CustomCategoryId.of( request.getCategoryId() ) )
                                                                  .userId( jwtManager.resolveUserId( accessToken ) )
                                                                  .contentId( ContentId.of( contentId ) )
                                                                  .build() ) ).build()
                                        )
                                   .build() );
    }
    
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
