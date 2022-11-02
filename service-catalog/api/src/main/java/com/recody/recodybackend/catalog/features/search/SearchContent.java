package com.recody.recodybackend.catalog.features.search;

import com.recody.recodybackend.common.contents.BasicCategory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchContent {
    
    private String keyword;
    @Deprecated
    private BasicCategory category;
    private String language;
    @Deprecated
    private Long userId;
    
}
