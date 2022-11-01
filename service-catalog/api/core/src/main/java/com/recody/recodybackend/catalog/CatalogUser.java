package com.recody.recodybackend.catalog;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CatalogUser {
    
    private Long userId;
    
}
