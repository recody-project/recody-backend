package com.recody.recodybackend.catalog.features.wish.add;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddToWishlist {
    
    private String contentId;
    private Long userId;
    
    @Override
    public String toString() {
        return "{\"AddToWishlist\":{" + "\"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"userId\":" + userId + "}}";
    }
}
