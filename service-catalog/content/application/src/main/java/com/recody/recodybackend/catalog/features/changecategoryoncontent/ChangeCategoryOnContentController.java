package com.recody.recodybackend.catalog.features.changecategoryoncontent;

import com.recody.recodybackend.catalog.web.ChangeCategoryOnContentResponse;
import com.recody.recodybackend.catalog.web.SetCustomCategoryRequest;
import com.recody.recodybackend.category.CustomCategoryId;
import com.recody.recodybackend.common.contents.ContentId;
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
class ChangeCategoryOnContentController {
    private final MessageSource ms;
    private final ChangeCategoryOnContentHandler changeCategoryOnContentHandler;
    private final JwtManager jwtManager;
    
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
    
}
