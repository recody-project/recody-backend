package com.recody.recodybackend.catalog.features.getcontents;

import com.recody.recodybackend.common.contents.ContentId;
import lombok.Builder;
import lombok.Data;

import java.util.Locale;

@Data
@Builder
public class GetContent {
    
    private ContentId contentId;
    private Locale locale;
    
    
}
