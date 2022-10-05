package com.recody.recodybackend.common.events;

import com.recody.recodybackend.common.contents.Category;
import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentCreated {
    
    private String catalogId;
    
    private String contentId;
    
    private String title;
    
    private String imageUrl;
    
    private Category category;
    
}
