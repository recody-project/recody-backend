package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.catalog.features.wish.add.AddToWishlist;
import com.recody.recodybackend.catalog.features.wish.add.AddToWishlistHandler;
import com.recody.recodybackend.catalog.features.wish.delete.DeleteFromWishlist;
import com.recody.recodybackend.catalog.features.wish.delete.DeleteFromWishlistHandler;
import com.recody.recodybackend.catalog.features.wish.get.GetMyWishlist;
import com.recody.recodybackend.catalog.features.wish.get.GetMyWishlistHandler;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.commonbootutils.web.AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class WishController {
    
    private final JwtManager jwtManager;
    private final MessageSource ms;
    
    private final AddToWishlistHandler addToWishlistHandler;
    
    private final DeleteFromWishlistHandler deleteFromWishlistHandler;
    
    private final GetMyWishlistHandler getMyWishlistHandler;
    
    @PostMapping("/api/v1/catalog/wish")
    public ResponseEntity<SuccessResponseBody> wish(HttpServletRequest httpServletRequest,
                                                    @RequestBody AddToWishlistRequest request,
                                                    @AccessToken String accessToken){
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                        .message(ms.getMessage("catalog.wish.add.succeeded", null, "위시 추가 성공", httpServletRequest.getLocale()))
                        .data(addToWishlistHandler.handle(AddToWishlist.builder()
                                                                  .userId(jwtManager.resolveUserId(accessToken))
                                                                  .contentId(request.getContentId())
                                                                  .category(Category.of(request.getCategory()))
                                                                       .build()))
                                   .build()
                                );
    }
    
    @DeleteMapping("/api/v1/catalog/wish")
    public ResponseEntity<SuccessResponseBody> deleteWish(HttpServletRequest httpServletRequest,
                                                    @Valid @RequestBody DeleteFromWishlistRequest request,
                                                    @AccessToken String accessToken){
        deleteFromWishlistHandler.handle(DeleteFromWishlist.builder()
                                                           .userId(jwtManager.resolveUserId(accessToken))
                                                           .contentId(request.getContentId())
                                                           .category(Category.of(request.getCategory()))
                                                           .build());
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message(ms.getMessage("catalog.wish.delete.succeeded", null, "위시 제거 성공", httpServletRequest.getLocale()))
                                   .data(null)
                                   .build()
                                );
        
    }
    
    @GetMapping("/api/v1/catalog/wish/wishes")
    public ResponseEntity<SuccessResponseBody> wishes(HttpServletRequest httpServletRequest,
                                                    @AccessToken String accessToken){
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message(ms.getMessage("catalog.wish.get-wishes.succeeded", null, "위시 추가 성공", httpServletRequest.getLocale()))
                                   .data(getMyWishlistHandler.handle(GetMyWishlist.builder()
                                                                                  .userId(jwtManager.resolveUserId(accessToken))
                                                                                  .build()))
                                   .build()
                                );
    }
}
