package com.recody.recodybackend.catalog.web;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyCategoryRequest {
    
    private String name;
    private String iconUrl;
    
}
