package com.recody.recodybackend.catalog.features.record.continuerecord;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ContinueRecord {
    
    @NonNull
    private String recordId;
    
    private String title;

    private String note;
    
}
