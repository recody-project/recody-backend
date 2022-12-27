package com.recody.recodybackend.catalog.features.category.delete;

import com.recody.recodybackend.category.CustomCategoryId;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteCategory {
    private CustomCategoryId customCategoryId;
    private Long userId;
    
    @Override
    public String toString() {
        return "{\"DeleteCategory\":{"
               + "\"categoryId\":" + customCategoryId
               + ", \"userId\":" + userId
               + "}}";
    }
}
