package com.recody.recodybackend.record.features.getcontinuingrecord;

import com.recody.recodybackend.record.Record;

public interface GetContinuingRecordHandler {
    
    Record handle(GetContinuingRecord command);
    
}
