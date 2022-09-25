package com.recody.recodybackend.record.features;

import com.recody.recodybackend.record.features.addrecord.AddRecord;
import com.recody.recodybackend.record.features.completerecord.CompleteRecord;
import com.recody.recodybackend.record.features.continuerecord.ContinueRecord;
import com.recody.recodybackend.record.features.getmyrecords.GetMyRecords;
import com.recody.recodybackend.record.features.getrecord.GetRecord;

public interface RecordService {
    
    AddRecordResponse addRecord(AddRecord command);
    GetRecordResponse getRecord(GetRecord command);
    GetRecordsResponse getRecords(GetMyRecords command);
    CompleteRecordResponse completeRecord(CompleteRecord command);
    ContinueRecordResponse continueRecord(ContinueRecord command);

}
