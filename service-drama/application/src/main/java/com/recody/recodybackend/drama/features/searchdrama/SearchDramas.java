package com.recody.recodybackend.drama.features.searchdrama;

import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchDramas {
    
    private String keyword;
    
    private Locale locale;
}
