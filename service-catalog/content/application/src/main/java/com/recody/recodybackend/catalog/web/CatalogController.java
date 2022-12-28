package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.catalog.features.changecategoryoncontent.ChangeCategoryOnContent;
import com.recody.recodybackend.catalog.features.changecategoryoncontent.ChangeCategoryOnContentHandler;
import com.recody.recodybackend.catalog.features.changegenreoncontent.ChangeGenresOnContent;
import com.recody.recodybackend.catalog.features.changegenreoncontent.ChangeGenresOnContentHandler;
import com.recody.recodybackend.catalog.features.content.getcontents.GetContent;
import com.recody.recodybackend.catalog.features.content.getcontents.GetContentHandler;
import com.recody.recodybackend.catalog.features.search.SearchContent;
import com.recody.recodybackend.catalog.features.search.SearchContentHandler;
import com.recody.recodybackend.catalog.features.search.SearchContentWithFilters;
import com.recody.recodybackend.category.CustomCategoryId;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.ContentId;
import com.recody.recodybackend.common.contents.GenreIds;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.commonbootutils.web.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
class CatalogController {
    
    private final SearchContentHandler searchContentHandler;
    private final GetContentHandler getContentHandler;
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
    
    /**
     * contentId 로 작품 정보를 가져온다.
     */
    @GetMapping( "/api/v1/catalog/content/{contentId}" )
    public ResponseEntity<SuccessResponseBody> getContent(@PathVariable String contentId,
                                                          HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.content.get.succeeded", null, httpServletRequest.getLocale() ) )
                                   .data( getContentHandler.handle( GetContent.builder()
                                                                              .contentId( ContentId.of( contentId ) )
                                                                              .locale( httpServletRequest.getLocale() )
                                                                              .build() ) )
                                   .build() );
    }
    
    @GetMapping( "/api/v1/catalog/search" )
    @Deprecated
    public ResponseEntity<SuccessResponseBody> search(@RequestParam String keyword,
                                                      @Nullable @RequestParam( defaultValue = "movie" ) String category,
                                                      @Nullable @RequestParam( defaultValue = "ko" ) String language,
                                                      @AccessToken String accessToken, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.search.succeeded", null, httpServletRequest.getLocale() ) )
                                   .data( searchContentHandler.handle(
                                           SearchContent.builder()
                                                        .keyword( keyword )
                                                        .category( BasicCategory.of( category ) )
                                                        .userId( jwtManager.resolveUserId( accessToken ) )
                                                        .language( language )
                                                        .build() ) )
                                   .build() );
    }
    
    @GetMapping( "/api/v2/catalog/search" )
    public ResponseEntity<SuccessResponseBody> searchV2(@RequestParam String keyword,
                                                        @Nullable @RequestParam( defaultValue = "all" ) String categoryId,
                                                        @Nullable @RequestParam( defaultValue = "ko" ) String language,
                                                        @AccessToken String accessToken,
                                                        HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.searchV2.succeeded", null, httpServletRequest.getLocale() ) )
                                   .data( searchContentHandler.handle(
                                           SearchContentWithFilters.builder()
                                                                   .keyword( keyword )
                                                                   .language( language )
                                                                   .categories( BasicCategory.isBasic( categoryId )
                                                                                        ? List.of( BasicCategory.idOf( categoryId ) )
                                                                                        : BasicCategory.all() )
                                                                   .build() ) )
                                   .build() );
    }
}
