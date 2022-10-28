package com.recody.recodybackend.catalog.features.rating.add;

import com.recody.recodybackend.catalog.features.ContentId;
import com.recody.recodybackend.catalog.features.RatingScore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddRating {
    
    private Long userId;
    private ContentId contentId;
    private RatingScore score;
}
