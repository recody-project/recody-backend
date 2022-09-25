package com.recody.recodybackend.record.features;

import com.recody.recodybackend.record.Record;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetContinuingRecordResponse {
    
    private Record record;
}
