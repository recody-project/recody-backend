package com.recody.recodybackend.drama;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Drama {
    
    private String id;
    private String title;
    private String imageUrl;
    
    
}
