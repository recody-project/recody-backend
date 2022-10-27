package com.recody.recodybackend.catalog.web;


import com.recody.recodybackend.catalog.features.category.add.AddCategory;
import com.recody.recodybackend.catalog.features.category.add.AddCategoryHandler;
import com.recody.recodybackend.catalog.features.category.add.AddCategoryResponse;
import com.recody.recodybackend.catalog.features.category.getmycategories.GetMyCategories;
import com.recody.recodybackend.catalog.features.category.getmycategories.GetMyCategoriesHandler;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.commonbootutils.web.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    
    private final JwtManager jwtManager;
    private final AddCategoryHandler addCategoryHandler;
    private final GetMyCategoriesHandler getMyCategoriesHandler;
    private final MessageSource ms;
    
    
    @PostMapping("/api/v1/catalog/category")
    public ResponseEntity<SuccessResponseBody> addCategory(@AccessToken String accessToken,
                                                           HttpServletRequest httpServletRequest,
                                                           @RequestBody AddCategoryRequest request
    
                                                          ) {
        return ResponseEntity.ok(SuccessResponseBody.builder()
                                                    .message(ms.getMessage("catalog.category.add.succeeded", null,
                                                                           "카테고리 추가 성공", httpServletRequest.getLocale()
                                                                          ))
                                                    .data(new AddCategoryResponse(addCategoryHandler.handle(
                                                            AddCategory.builder()
                                                                       .iconUrl(request.getIconUrl())
                                                                       .userId(jwtManager.resolveUserId(accessToken))
                                                                       .name(request.getName())
                                                                       .build())))
                                                    .build());
        
    }
    
    @GetMapping("/api/v1/catalog/categories")
    public ResponseEntity<SuccessResponseBody> getAll(@AccessToken String accessToken,
                                                           HttpServletRequest httpServletRequest
                                                          ){
        return ResponseEntity.ok(SuccessResponseBody.builder()
                                                    .message(ms.getMessage("catalog.category.add.succeeded", null,
                                                                           "카테고리 추가 성공", httpServletRequest.getLocale()))
                                                    .data(getMyCategoriesHandler.handle(
                                                            GetMyCategories.builder()
                                                                    .userId(jwtManager.resolveUserId(accessToken))
                                                                           .build())
                                                         ).build());
    }
}
