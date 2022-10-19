package com.recody.recodybackend.catalog.features.search;

import com.recody.recodybackend.catalog.PersonalizedContent;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchContentResponse {
    
    public List<? extends PersonalizedContent> contents;
    
    public SearchContentResponse(List<? extends PersonalizedContent> contents) {
        this.contents = contents;
    }
}
