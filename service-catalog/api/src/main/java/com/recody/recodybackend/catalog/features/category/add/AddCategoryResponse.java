package com.recody.recodybackend.catalog.features.category.add;

import com.recody.recodybackend.common.contents.Category;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCategoryResponse {
    
    private Category category;
    
}
