package com.recody.recodybackend.catalog.features.record.getcontinuingrecord;

import com.recody.recodybackend.record.Record;

public interface GetContinuingRecordHandler {
    
    Record handle(GetContinuingRecord command);
    
}
