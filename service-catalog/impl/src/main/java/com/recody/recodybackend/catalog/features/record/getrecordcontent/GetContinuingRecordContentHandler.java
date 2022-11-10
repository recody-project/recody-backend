package com.recody.recodybackend.catalog.features.record.getrecordcontent;

import com.recody.recodybackend.record.RecordContent;

public interface GetContinuingRecordContentHandler {
    
    RecordContent handle(GetContinuingRecordContent command);
    
}
