package com.recody.recodybackend.catalog.features.record.getmyrecords;

import com.recody.recodybackend.common.data.QueryResult;
import com.recody.recodybackend.record.Record;

import java.util.List;

public interface GetMyRecordsHandler {
    
    List<Record> handle(GetMyRecords command);
    
    QueryResult<Record> handleQuery(GetMyRecords command);
    
}
