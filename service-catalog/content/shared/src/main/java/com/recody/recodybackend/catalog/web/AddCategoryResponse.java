package com.recody.recodybackend.catalog.web;

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
