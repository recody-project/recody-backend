package com.recody.recodybackend.common.events;

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
