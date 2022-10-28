package com.recody.recodybackend.catalog.web.rating;

import com.recody.recodybackend.catalog.features.Rating;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMyRatingResponse {
    
    private Rating rating;
    
}
