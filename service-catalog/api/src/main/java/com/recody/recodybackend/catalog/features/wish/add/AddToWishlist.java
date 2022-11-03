package com.recody.recodybackend.catalog.features.wish.add;

import com.recody.recodybackend.catalog.ContentId;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddToWishlist {
    
    private ContentId contentId;
    private Long userId;
    
    @Override
    public String toString() {
        return "{\"AddToWishlist\":{" + "\"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"userId\":" + userId + "}}";
    }
}
