package com.recody.recodybackend.catalog.features.search;

import com.recody.recodybackend.catalog.web.SearchContentWithFiltersResponseV3;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.commonbootutils.web.AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
class SearchContentController {
    
    private final MessageSource ms;
    private final SearchContentHandler searchContentHandler;
    private final SearchContentWithFiltersHandler<SearchContentWithFiltersResponseV3> searchContentHandlerV3;
    private final JwtManager jwtManager;
    
    @GetMapping( "/api/v1/catalog/search" )
    @Deprecated
    public ResponseEntity<SuccessResponseBody> search(@RequestParam String keyword,
                                                      @Nullable @RequestParam( defaultValue = "movie" ) String category,
                                                      @Nullable @RequestParam( defaultValue = "ko" ) String language,
                                                      @AccessToken String accessToken,
                                                      HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.search.succeeded", null,
                                                            httpServletRequest.getLocale() ) )
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
                                                        HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.searchV2.succeeded", null,
                                                            httpServletRequest.getLocale() ) )
                                   .data( searchContentHandler.handle(
                                           SearchContentWithFilters.builder()
                                                                   .keyword( keyword )
                                                                   .language( language )
                                                                   .categories(
                                                                           BasicCategory.isBasic( categoryId )
                                                                                   ? List.of(
                                                                                   BasicCategory.idOf(
                                                                                           categoryId ) )
                                                                                   : BasicCategory.all() )
                                                                   .build() ) )
                                   .build() );
    }
    
    @GetMapping( "/api/v3/catalog/search" )
    public ResponseEntity<SuccessResponseBody> searchV3(
            @RequestParam String keyword,
            @Nullable @RequestParam( defaultValue = "all" ) String categoryId,
            @Nullable @RequestParam( defaultValue = "ko" ) String language,
            @RequestParam( required = false ) List<String> genreIds,
            @RequestParam( required = false, defaultValue = "1" ) Integer page,
            HttpServletRequest httpServletRequest) {
        
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.searchV2.succeeded", null,
                                                            httpServletRequest.getLocale() ) )
                                   .data( searchContentHandlerV3.handle(
                                           SearchContentWithFilters.builder()
                                                                   .keyword( keyword )
                                                                   .language( language )
                                                                   .categories(
                                                                           BasicCategory.isBasic( categoryId )
                                                                                   ? List.of(
                                                                                   BasicCategory.idOf(
                                                                                           categoryId ) )
                                                                                   : BasicCategory.all() )
                                                                   .genreIds( genreIds )
                                                                   .page( page )
                                                                   .build() ) )
                                   .build() );
    }
    
}
