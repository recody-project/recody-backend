package com.recody.recodybackend.catalog.features.rate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddRating {
    
    private Long userId;
    private String contentId;
    private Integer score;
}
