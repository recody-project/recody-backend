package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.catalog.features.LeaveRatingResponse;
import com.recody.recodybackend.catalog.features.rate.AddRating;
import com.recody.recodybackend.catalog.features.rate.AddRatingHandler;
import com.recody.recodybackend.catalog.web.rating.LeaveRatingRequest;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import com.recody.recodybackend.commonbootutils.jwt.JwtManager;
import com.recody.recodybackend.commonbootutils.web.AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class RatingController {
    
    private final AddRatingHandler leaveRatingHandler;
    private final JwtManager jwtManager;
    private final MessageSource ms;
    
    
    @PostMapping("/api/v1/catalog/rating")
    public ResponseEntity<SuccessResponseBody> addRating(@AccessToken String accessToken,
                                                      HttpServletRequest httpServletRequest,
                                                      @RequestBody LeaveRatingRequest request){
        return ResponseEntity.ok(SuccessResponseBody.builder()
                                         .message(ms.getMessage("catalog.rating.add.succeeded", null, httpServletRequest.getLocale()))
                                         .data(new LeaveRatingResponse(leaveRatingHandler.handle(getLeaveRating(accessToken, request))))
                                                    .build());
    }
    
    private AddRating getLeaveRating(String accessToken, LeaveRatingRequest request) {
        return AddRating.builder()
                        .contentId(request.getContentId())
                        .score(request.getScore())
                        .userId(jwtManager.resolveUserId(accessToken))
                        .build();
    }
    
}
