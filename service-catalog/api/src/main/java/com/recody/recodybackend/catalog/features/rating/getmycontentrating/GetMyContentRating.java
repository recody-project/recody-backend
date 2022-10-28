package com.recody.recodybackend.catalog.features.rating.getmycontentrating;

import com.recody.recodybackend.catalog.features.ContentId;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetMyContentRating {
    
    private Long userId;
    private ContentId contentId;
    
}
