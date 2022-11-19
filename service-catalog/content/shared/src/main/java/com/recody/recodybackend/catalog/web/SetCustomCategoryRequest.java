package com.recody.recodybackend.catalog.web;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetCustomCategoryRequest {
    
    private String categoryId;
    
}
