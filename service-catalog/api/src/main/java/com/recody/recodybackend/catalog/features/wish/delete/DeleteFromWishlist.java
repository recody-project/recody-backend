package com.recody.recodybackend.catalog.features.wish.delete;

import com.recody.recodybackend.catalog.ContentId;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteFromWishlist {
    
    private ContentId contentId;
    private Long userId;
    
    @Override
    public String toString() {
        return "{\"DeleteFromWishlist\":{" + "\"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"userId\":" + userId + "}}";
    }
    
}
