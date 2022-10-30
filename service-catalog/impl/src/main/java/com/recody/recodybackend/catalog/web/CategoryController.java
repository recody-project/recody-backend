package com.recody.recodybackend.catalog.web;


import com.recody.recodybackend.catalog.features.CategoryIconUrl;
import com.recody.recodybackend.catalog.features.CustomCategoryId;
import com.recody.recodybackend.catalog.features.CategoryName;
import com.recody.recodybackend.catalog.features.category.add.AddCategory;
import com.recody.recodybackend.catalog.features.category.add.AddCategoryHandler;
import com.recody.recodybackend.catalog.features.category.delete.DeleteCategory;
import com.recody.recodybackend.catalog.features.category.delete.DeleteCategoryHandler;
import com.recody.recodybackend.catalog.features.category.getmycategories.GetMyCategories;
import com.recody.recodybackend.catalog.features.category.getmycategories.GetMyCategoriesHandler;
import com.recody.recodybackend.catalog.features.category.modify.ModifyCategory;
import com.recody.recodybackend.catalog.features.category.modify.ModifyCategoryHandler;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.commonbootutils.web.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    
    private final JwtManager jwtManager;
    private final AddCategoryHandler addCategoryHandler;
    
    private final ModifyCategoryHandler modifyCategoryHandler;
    
    private final DeleteCategoryHandler deleteCategoryHandler;
    private final GetMyCategoriesHandler getMyCategoriesHandler;
    private final MessageSource ms;
    
    
    @PostMapping( "/api/v1/catalog/category" )
    public ResponseEntity<SuccessResponseBody> addCategory(@AccessToken String accessToken,
                                                           HttpServletRequest httpServletRequest,
                                                           @Valid @RequestBody AddCategoryRequest request
                                                          ) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.category.add.succeeded", null,
                                                            "카테고리 추가 성공", httpServletRequest.getLocale() ) )
                                   .data( new AddCategoryResponse(
                                           addCategoryHandler.handle(
                                                   AddCategory.builder()
                                                              .iconUrl( CategoryIconUrl.of( request.getIconUrl() ) )
                                                              .userId( jwtManager.resolveUserId( accessToken ) )
                                                              .name( CategoryName.of( request.getName() ) )
                                                              .build() ) ) )
                                   .build() );
    }
    
    @DeleteMapping( "/api/v1/catalog/category/{categoryId}" )
    public ResponseEntity<SuccessResponseBody> deleteCategory(@AccessToken String accessToken,
                                                              @PathVariable String categoryId,
                                                              HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.category.delete.succeeded", null,
                                                            "카테고리 삭제 성공", httpServletRequest.getLocale() ) )
                                   .data( new DeleteCategoryResponse( deleteCategoryHandler.handle(
                                           DeleteCategory.builder()
                                                         .userId( jwtManager.resolveUserId( accessToken ) )
                                                         .customCategoryId( CustomCategoryId.of( categoryId ) )
                                                         .build() ) ) )
                                   .build() );
    }
    
    @PutMapping( "/api/v1/catalog/category/{categoryId}" )
    public ResponseEntity<SuccessResponseBody> modifyCategory(@AccessToken String accessToken,
                                                              @PathVariable String categoryId,
                                                              @Valid @RequestBody ModifyCategoryRequest request,
                                                              HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.category.modify.succeeded", null,
                                                            "카테고리 변경 성공", httpServletRequest.getLocale() ) )
                                   .data( new ModifyCategoryResponse(
                                           modifyCategoryHandler.handle(
                                                   ModifyCategory.builder()
                                                                 .userId( jwtManager.resolveUserId( accessToken ) )
                                                                 .categoryId( CustomCategoryId.of( categoryId ) )
                                                                 .categoryName( CategoryName.of( request.getName() ) )
                                                                 .categoryIconUrl( CategoryIconUrl.of( request.getIconUrl() ) )
                                                                 .build() ) ) )
                                   .build() );
    }
    
    @GetMapping( "/api/v1/catalog/categories" )
    public ResponseEntity<SuccessResponseBody> getAll(@AccessToken String accessToken,
                                                      HttpServletRequest httpServletRequest
                                                     ) {
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message( ms.getMessage( "catalog.category.add.succeeded", null,
                                                            "카테고리 추가 성공", httpServletRequest.getLocale() ) )
                                   .data( new GetMyCategoriesResponse(
                                           getMyCategoriesHandler.handle(
                                                   GetMyCategories.builder()
                                                                  .userId( jwtManager.resolveUserId( accessToken ) )
                                                                  .build() ) )
                                        )
                                   .build() );
    }
}
