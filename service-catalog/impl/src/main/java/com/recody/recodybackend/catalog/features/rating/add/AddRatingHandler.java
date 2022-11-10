package com.recody.recodybackend.catalog.features.rating.add;


import java.util.UUID;

/**
 * 유저가 작품에 별점을 남긴다.
 * 별점은 10점 체계로 이루어진다. 1 ~ 10
 *
 */
public interface AddRatingHandler {
    
    /**
     * @param command userId, contentId, score
     * @return rating 한 eventId
     */
    UUID handle(AddRating command);
}
