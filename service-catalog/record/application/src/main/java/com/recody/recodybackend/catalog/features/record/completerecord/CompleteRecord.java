package com.recody.recodybackend.catalog.features.record.completerecord;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class CompleteRecord {
    
    @NonNull
    private String recordId;
    
    private Long userId;
    
    @Override
    public String toString() {
        return "{\"CompleteRecord\":{"
               + "\"recordId\":" + ((recordId != null) ? ("\"" + recordId + "\"") : null)
               + ", \"userid\":" + userId
               + "}}";
    }
}
