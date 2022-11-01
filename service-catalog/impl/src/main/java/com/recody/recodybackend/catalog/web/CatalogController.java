package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.catalog.ContentId;
import com.recody.recodybackend.catalog.CustomCategoryId;
import com.recody.recodybackend.catalog.features.content.getcontents.GetContent;
import com.recody.recodybackend.catalog.features.content.getcontents.GetContentHandler;
import com.recody.recodybackend.catalog.features.content.getdetail.GetContentDetail;
import com.recody.recodybackend.catalog.features.content.getdetail.GetContentDetailHandler;
import com.recody.recodybackend.catalog.features.content.getdetail.movie.GetMovieDetail;
import com.recody.recodybackend.catalog.features.content.getdetail.movie.GetMovieDetailHandler;
import com.recody.recodybackend.catalog.features.search.SearchContent;
import com.recody.recodybackend.catalog.features.search.SearchContentHandler;
import com.recody.recodybackend.catalog.features.setcustomcategory.SetCustomCategoryOnContent;
import com.recody.recodybackend.catalog.features.setcustomcategory.SetCustomCategoryOnContentHandler;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.events.MMM;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.commonbootutils.web.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
class CatalogController {
    
    private final SearchContentHandler searchContentHandler;
    private final GetContentDetailHandler getContentDetailHandler;
    
    private final GetMovieDetailHandler getMovieDetailHandler;
    
    private final GetContentHandler getContentHandler;
    
    private final SetCustomCategoryOnContentHandler setCustomCategoryOnContentHandler;
    private final JwtManager jwtManager;
    private final MessageSource ms;
    
    private final KafkaTemplate<String, String> kt;
    private final KafkaTemplate<String, MMM> kt2;
    
    @GetMapping( "/api/v1/catalog/detail" )
    public ResponseEntity<SuccessResponseBody> detail(@RequestParam Integer contentId,
                                                      @Nullable @RequestParam( defaultValue = "ko" ) String language,
                                                      @AccessToken String accessToken, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.content.detail.get.succeeded", null, httpServletRequest.getLocale() ) )
                                   .data( new GetContentDetailResponse(
                                           getContentDetailHandler.handle(
                                                   GetContentDetail.builder()
                                                                   .movieId( contentId )
                                                                   .language( language )
                                                                   .category( BasicCategory.Movie )
                                                                   .userId( jwtManager.resolveUserId( accessToken ) )
                                                                   .build() ) ) )
                                   .build() );
    }
    
    /**
     * contentId 로 작품 정보를 가져온다.
     */
    @GetMapping( "/api/v1/catalog/content/{contentId}" )
    public ResponseEntity<SuccessResponseBody> getContent(@PathVariable String contentId, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.content.get.succeeded", null, httpServletRequest.getLocale() ) )
                                   .data( getContentHandler.handle( GetContent.builder()
                                                                              .contentId( ContentId.of( contentId ) )
                                                                              .build() ) )
                                   .build() );
    }
    
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
    
    @PostMapping( "/kafka/publish" )
    public String publish(@RequestBody String msg) {
        log.info( "msg: {} 받았습니다.", msg );
        kt.send( "testTopic", msg );
        return "보냈습니다.";
    }
    
    @PostMapping( "/kafka/publish2" )
    public String publish2(@RequestBody MMM mmm) {
        log.info( "msg: {} 받았습니다.", mmm );
        kt2.send( "testTopic2", mmm );
        return "보냈습니다." + mmm;
    }
    
    @GetMapping( "/api/v1/catalog/search" )
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
    
    @PostMapping( "/api/v1/catalog/content/{contentId}" )
    public ResponseEntity<SuccessResponseBody> changeContentInfo(@AccessToken String accessToken,
                                                                 HttpServletRequest httpServletRequest,
                                                                 @PathVariable String contentId,
                                                                 @RequestBody SetCustomCategoryRequest request
                                                                ) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .data( SetCustomCategoryResponse
                                                  .builder()
                                                  .event( setCustomCategoryOnContentHandler.handle(
                                                          SetCustomCategoryOnContent
                                                                  .builder()
                                                                  .categoryId( CustomCategoryId.of( request.getCategoryId() ) )
                                                                  .userId( jwtManager.resolveUserId( accessToken ) )
                                                                  .contentId( ContentId.of( contentId ) )
                                                                  .build() ) ).build()
                                        )
                                   .build() );
    }
}
