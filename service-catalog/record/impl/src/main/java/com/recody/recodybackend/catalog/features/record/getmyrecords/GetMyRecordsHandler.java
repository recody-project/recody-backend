package com.recody.recodybackend.catalog.features.record.getmyrecords;

import com.recody.recodybackend.record.Record;

import java.util.List;

public interface GetMyRecordsHandler {
    
    List<Record> handle(GetMyRecords command);
    
}
