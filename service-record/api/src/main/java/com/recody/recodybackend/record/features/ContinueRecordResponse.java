package com.recody.recodybackend.record.features;

import com.recody.recodybackend.record.RecordEvent;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ContinueRecordResponse {
    
    private List<RecordEvent> events;
    
}
