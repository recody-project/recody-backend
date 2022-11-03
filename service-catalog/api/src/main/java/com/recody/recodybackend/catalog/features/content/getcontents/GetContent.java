package com.recody.recodybackend.catalog.features.content.getcontents;

import com.recody.recodybackend.catalog.ContentId;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetContent {
    
    private ContentId contentId;
    
    
}
