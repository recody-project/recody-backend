package com.recody.recodybackend.catalog.features.record.totalrecords;

import com.recody.recodybackend.record.RecordCount;

public interface CountTotalRecordsHandler {
    
    RecordCount handle(CountTotalRecords command);
    
    
    
}
