package com.recody.recodybackend.record.features.totalrecords;

import com.recody.recodybackend.record.RecordCount;

public interface CountTotalRecordsHandler {
    
    RecordCount handle(CountTotalRecords command);
    
    
    
}
