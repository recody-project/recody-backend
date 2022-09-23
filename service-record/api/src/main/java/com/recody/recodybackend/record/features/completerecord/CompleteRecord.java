package com.recody.recodybackend.record.features.completerecord;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class CompleteRecord {
    
    @NonNull
    private String recordId;
    
    private String note;
}
