package com.recody.recodybackend.event;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryPersonalized {
    
    @Builder.Default
    private CatalogEventType type = CatalogEventType.CategoryPersonalized;
    
    private Long userId;
    private String contentId;
    private String categoryId;

}
