package com.recody.recodybackend.record.features;

import com.recody.recodybackend.record.RecordEvent;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ContinueRecordResponse {
    
    private String recordId;
    private List<RecordEvent> events;
    
    @Override
    public String toString() {
        return "{\"ContinueRecordResponse\":{" + "\"recordId\":" + ((recordId != null) ? ("\"" + recordId + "\"") : null) + ", \"events\":" + events + "}}";
    }
}
