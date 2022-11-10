package com.recody.recodybackend.catalog.features.category.getmycategories;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetMyCategories {
    
    private Long userId;
    
}
