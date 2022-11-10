package com.recody.recodybackend;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Deprecated
public class CatalogUser {
    
    private Long userId;
    
}
