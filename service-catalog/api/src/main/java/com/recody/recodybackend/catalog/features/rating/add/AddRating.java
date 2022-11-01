package com.recody.recodybackend.catalog.features.rating.add;

import com.recody.recodybackend.catalog.ContentId;
import com.recody.recodybackend.catalog.RatingScore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddRating {
    
    private Long userId;
    private ContentId contentId;
    private RatingScore score;
}
