package com.recody.recodybackend.record.features;

import com.recody.recodybackend.record.features.addrecord.AddRecord;
import com.recody.recodybackend.record.features.getmyrecords.GetMyRecords;
import com.recody.recodybackend.record.features.getrecord.GetRecord;

public interface RecordService {
    
    AddRecordResponse addRecord(AddRecord command);
    GetRecordResponse getRecord(GetRecord command);
    GetRecordsResponse getRecords(GetMyRecords command);

}
