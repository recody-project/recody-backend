package com.recody.recodybackend.record.features;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AddRecordResponse {
    private String recordId;
    
    @Override
    public String toString() {
        return "{\"AddRecordResponse\":{" + "\"recordId\":" + ((recordId != null) ? ("\"" + recordId + "\"") : null) + "}}";
    }
}
