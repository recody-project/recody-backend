package com.recody.recodybackend.catalog.features.getcontents;

import com.recody.recodybackend.common.contents.ContentId;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
class GetContentController {
    
    
    private final MessageSource ms;
    private final GetContentHandler getContentHandler;
    
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
    
}
