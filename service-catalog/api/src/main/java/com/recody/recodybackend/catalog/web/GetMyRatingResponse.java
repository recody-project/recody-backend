package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.catalog.Rating;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMyRatingResponse {
    
    private Rating rating;
    
}
