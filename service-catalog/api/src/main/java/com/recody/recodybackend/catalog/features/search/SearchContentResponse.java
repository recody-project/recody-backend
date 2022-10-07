package com.recody.recodybackend.catalog.features.search;

import com.recody.recodybackend.catalog.PersonalizedContentDetail;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchContentResponse {
    
    public List<? extends PersonalizedContentDetail> contents;
    
    public SearchContentResponse(List<? extends PersonalizedContentDetail> contents) {
        this.contents = contents;
    }
}
