package com.recody.recodybackend.common.events;

import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentCreated {
    
    private String catalogId;
    
    private String contentId;
    
    private String koreanTitle;
    
    private String englishTitle;
    
    private String imageUrl;
    
    private String categoryId;
    
    private String categoryName;
    
}
