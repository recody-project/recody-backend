package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.common.contents.ContentId;
import com.recody.recodybackend.rating.RatingScore;
import com.recody.recodybackend.catalog.features.rating.add.AddRating;
import com.recody.recodybackend.catalog.features.rating.add.AddRatingHandler;
import com.recody.recodybackend.catalog.features.rating.getmycontentrating.GetMyContentRating;
import com.recody.recodybackend.catalog.features.rating.getmycontentrating.GetMyContentRatingHandler;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.commonbootutils.web.AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class RatingController {
    
    private final AddRatingHandler leaveRatingHandler;
    
    private final GetMyContentRatingHandler getMyContentRatingHandler;
    private final JwtManager jwtManager;
    private final MessageSource ms;
    
    
    @PostMapping( "/api/v1/catalog/rating" )
    public ResponseEntity<SuccessResponseBody> addRating(@AccessToken String accessToken,
                                                         HttpServletRequest httpServletRequest,
                                                         @RequestBody AddRatingRequest request) {
        return ResponseEntity.ok(SuccessResponseBody.builder()
                                                    .message(ms.getMessage("catalog.rating.add.succeeded", null,
                                                                           httpServletRequest.getLocale()))
                                                    .data(new AddRatingResponse( leaveRatingHandler.handle( createAddRating( accessToken, request ) )) )
                                                    .build());
    }
    
    @GetMapping("/api/v1/catalog/rating")
    public ResponseEntity<SuccessResponseBody> getRating(@AccessToken String accessToken,
                                                         HttpServletRequest httpServletRequest,
                                                         @RequestParam String contentId){
        return ResponseEntity.ok(
                SuccessResponseBody.builder()
                                   .message(ms.getMessage("catalog.rating.get.succeeded",
                                                          null,
                                                          httpServletRequest.getLocale()))
                                   .data(new GetMyRatingResponse(
                                           getMyContentRatingHandler.handle(
                                                   GetMyContentRating.builder()
                                                                     .contentId( ContentId.of( contentId ) )
                                                                     .userId( jwtManager.resolveUserId( accessToken ) )
                                                                     .build() )))
                                   .build());
    }
    
    private AddRating createAddRating(String accessToken, AddRatingRequest request) {
        return AddRating.builder()
                        .contentId( ContentId.of( request.getContentId() ) )
                        .score(RatingScore.of(request.getScore()))
                        .userId(jwtManager.resolveUserId(accessToken))
                        .build();
    }
}
