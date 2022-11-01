package com.recody.recodybackend.catalog.features.setcustomcategory;

import com.recody.recodybackend.common.events.CategoryEventType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryPersonalized {
    
    @Builder.Default
    private CategoryEventType type = CategoryEventType.CategoryPersonalized;
    
    private Long userId;
    private String contentId;
    private String categoryId;

}
