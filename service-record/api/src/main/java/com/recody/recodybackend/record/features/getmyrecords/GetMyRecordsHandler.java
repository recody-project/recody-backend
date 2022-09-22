package com.recody.recodybackend.record.features.getmyrecords;

import com.recody.recodybackend.record.Record;

import java.util.List;

public interface GetMyRecordsHandler {
    
    List<Record> handle(GetMyRecords command);
    
}
