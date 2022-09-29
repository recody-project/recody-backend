package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.catalog.features.getdetail.GetContentDetail;
import com.recody.recodybackend.catalog.features.getdetail.GetContentDetailHandler;
import com.recody.recodybackend.catalog.features.search.SearchContent;
import com.recody.recodybackend.catalog.features.search.SearchContentHandler;
import com.recody.recodybackend.common.contents.Category;
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

@RestController
@RequiredArgsConstructor
class CatalogController {
    
    private final SearchContentHandler searchContentHandler;
    private final GetContentDetailHandler getContentDetailHandler;
    private final JwtManager jwtManager;
    private final MessageSource ms;

    @GetMapping("/api/v1/catalog/search")
    public ResponseEntity<SuccessResponseBody> search(@RequestParam String keyword,
                                                      @Nullable @RequestParam(defaultValue = "movie") String category,
                                                      @AccessToken String accessToken,
                                                      HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(SuccessResponseBody.builder()
                                                    .message(ms.getMessage("catalog.search.succeeded", null, httpServletRequest.getLocale()))
                                                    .data(searchContentHandler.handle(
                                                            SearchContent.builder()
                                                                         .keyword(keyword)
                                                                         .category(Category.of(category))
                                                                         .userId(jwtManager.resolveUserId(accessToken))
                                                                         .build()))
                                                    .build());
    }
    
    @GetMapping("/api/v1/catalog/detail")
    public ResponseEntity<SuccessResponseBody> detail(@RequestParam String contentId,
                                                      @Nullable @RequestParam(defaultValue = "movie") String category,
                                                      @Nullable @RequestParam(defaultValue = "ko") String language,
                                                      @AccessToken String accessToken,
                                                      HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(SuccessResponseBody.builder()
                                                    .message(ms.getMessage("catalog.get-detail.succeeded", null, httpServletRequest.getLocale()))
                                                    .data(getContentDetailHandler.handle(GetContentDetail.builder()
                                                                                                 .contentId(contentId)
                                                                                                 .language(language)
                                                                                                 .category(Category.of(category))
                                                                                                 .userId(jwtManager.resolveUserId(accessToken))
                                                                                                         .build()))
                                                    .build());
    }
}
