package com.recody.recodybackend.catalog.features.changecategoryoncontent;

import com.recody.recodybackend.catalog.ContentId;
import com.recody.recodybackend.catalog.CustomCategoryId;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeCategoryOnContent {
    
    private Long userId;
    private ContentId contentId;
    private CustomCategoryId categoryId;
    
    @Override
    public String toString() {
        return "{\"ChangeCategoryOnContent\":{"
               + "\"userId\":" + userId
               + ", \"contentId\":" + contentId
               + ", \"categoryId\":" + categoryId
               + "}}";
    }
}
