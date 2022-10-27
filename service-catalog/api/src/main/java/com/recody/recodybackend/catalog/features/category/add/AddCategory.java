package com.recody.recodybackend.catalog.features.category.add;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddCategory {
    
    private String name;
    private Long userId;
    private String iconUrl;
    
}
