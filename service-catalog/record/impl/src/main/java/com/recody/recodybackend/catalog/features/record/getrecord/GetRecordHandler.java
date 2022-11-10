package com.recody.recodybackend.catalog.features.record.getrecord;

import com.recody.recodybackend.record.Record;

public interface GetRecordHandler {
    
    Record handle(GetRecord command);
    
}
