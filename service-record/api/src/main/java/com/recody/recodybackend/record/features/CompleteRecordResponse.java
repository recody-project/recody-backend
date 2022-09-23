package com.recody.recodybackend.record.features;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class CompleteRecordResponse {
    
    private String recordId;
    private boolean completed;
    
    @Override
    public String toString() {
        return "{\"CompleteRecordResponse\":{" + "\"recordId\":" + ((recordId != null) ? ("\"" + recordId + "\"") : null) + ", \"completed\":" + completed + "}}";
    }
}
