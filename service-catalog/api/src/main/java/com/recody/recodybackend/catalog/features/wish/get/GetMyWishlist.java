package com.recody.recodybackend.catalog.features.wish.get;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetMyWishlist {
    
    private Long userId;
    
}
