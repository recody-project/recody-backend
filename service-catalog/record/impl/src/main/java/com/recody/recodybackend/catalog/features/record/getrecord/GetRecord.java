package com.recody.recodybackend.catalog.features.record.getrecord;

import lombok.Builder;
import lombok.Getter;

@Builder
public class GetRecord {
    
    @Getter
    private String recordId;
    
    @Override
    public String toString() {
        return "{\"GetRecord\":{" + "\"recordId\":" + ((recordId != null) ? ("\"" + recordId + "\"") : null) + "}}";
    }
}
