package com.recody.recodybackend.record.features.getrecord;

import com.recody.recodybackend.record.Record;

public interface GetRecordHandler {
    
    Record handle(GetRecord command);
    
}
