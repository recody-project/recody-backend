package com.recody.recodybackend.catalog.features.record.completerecord;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class CompleteRecord {
    
    @NonNull
    private String recordId;
    private String title;
    
    private String note;
}
