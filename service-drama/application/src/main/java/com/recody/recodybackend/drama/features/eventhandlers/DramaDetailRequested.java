package com.recody.recodybackend.drama.features.eventhandlers;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DramaDetailRequested {
    
    private String dramaId;
    
}
