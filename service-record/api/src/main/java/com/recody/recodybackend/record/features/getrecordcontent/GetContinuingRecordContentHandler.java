package com.recody.recodybackend.record.features.getrecordcontent;

import com.recody.recodybackend.record.RecordContent;

public interface GetContinuingRecordContentHandler {
    
    RecordContent handle(GetContinuingRecordContent command);
    
}
