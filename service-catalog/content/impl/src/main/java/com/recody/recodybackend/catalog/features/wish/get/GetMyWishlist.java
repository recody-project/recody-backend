package com.recody.recodybackend.catalog.features.wish.get;

import lombok.Builder;
import lombok.Data;

import java.util.Locale;

@Data
@Builder
public class GetMyWishlist {
    
    private Long userId;
    private Locale locale;
    
}
