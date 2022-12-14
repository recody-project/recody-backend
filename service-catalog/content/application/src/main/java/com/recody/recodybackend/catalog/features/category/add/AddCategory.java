package com.recody.recodybackend.catalog.features.category.add;

import com.recody.recodybackend.category.CategoryIconUrl;
import com.recody.recodybackend.category.CategoryName;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddCategory {
    
    private CategoryName name;
    private Long userId;
    private CategoryIconUrl iconUrl;
    
    @Override
    public String toString() {
        return "{\"AddCategory\":{"
               + "\"name\":" + name
               + ", \"userId\":" + userId
               + ", \"iconUrl\":" + iconUrl
               + "}}";
    }
}
