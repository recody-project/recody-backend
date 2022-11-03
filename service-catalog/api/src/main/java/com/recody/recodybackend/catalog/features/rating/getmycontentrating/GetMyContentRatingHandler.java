package com.recody.recodybackend.catalog.features.rating.getmycontentrating;

import com.recody.recodybackend.catalog.Rating;

/**
 * 유저가 작품에 남긴 별점을 반환합니다.
 * @author motive
 */
public interface GetMyContentRatingHandler {
    
    /**
     * @param command 유저 id 와 작품 id
     * @return 별점을 남기지 않은 경우: UnRated 객체 <br>
     *         별점을 남긴 경우: RatingScore 객체
     */
    Rating handle(GetMyContentRating command);
    
}
