package com.recody.recodybackend.catalog.features.search.drama;

import lombok.*;

import java.util.List;
import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchDramas {
    
    private String keyword;
    private Locale locale;
    private List<String> genreIds;
    private Integer page;
    
}
