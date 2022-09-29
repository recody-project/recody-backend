package com.recody.recodybackend.catalog.features.search;

import com.recody.recodybackend.common.contents.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchContent {
    
    private String keyword;
    private Category category;
    private String language;
    private Long userId;
    
}
