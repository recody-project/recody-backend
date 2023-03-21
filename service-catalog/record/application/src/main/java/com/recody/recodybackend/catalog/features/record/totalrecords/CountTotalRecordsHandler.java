package com.recody.recodybackend.catalog.features.record.totalrecords;

public interface CountTotalRecordsHandler<R> {
    
    R handle(CountTotalRecords command);
}
