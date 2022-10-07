package com.recody.recodybackend.catalog.features.wish.add;

import com.recody.recodybackend.common.contents.BasicCategory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddToWishlist {
    
    private String contentId;
    private BasicCategory category;
    private Long userId;
    
    @Override
    public String toString() {
        return "{\"AddToWishlist\":{" + "\"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"category\":" + ((category != null) ? ("\"" + category + "\"") : null) + ", \"userId\":" + userId + "}}";
    }
}
