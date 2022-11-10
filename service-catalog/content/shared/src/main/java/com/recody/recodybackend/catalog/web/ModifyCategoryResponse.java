package com.recody.recodybackend.catalog.web;

import com.recody.recodybackend.category.CustomCategory;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyCategoryResponse {
    
    private CustomCategory category;
    
}
