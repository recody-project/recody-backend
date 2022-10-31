package com.recody.recodybackend.record.features;

import com.recody.recodybackend.record.features.addrecord.AddRecord;
import com.recody.recodybackend.record.features.completerecord.CompleteRecord;
import com.recody.recodybackend.record.features.continuerecord.ContinueRecord;
import com.recody.recodybackend.record.features.deleterecord.DeleteRecord;
import com.recody.recodybackend.record.features.getcontinuingrecord.GetContinuingRecord;
import com.recody.recodybackend.record.features.getmyrecords.GetMyRecords;
import com.recody.recodybackend.record.features.getrecord.GetRecord;
import com.recody.recodybackend.record.features.getrecordcontents.GetRecordContents;
import com.recody.recodybackend.record.web.*;

public interface RecordService {
    
    AddRecordResponse addRecord(AddRecord command);
    GetRecordResponse getRecord(GetRecord command);
    GetRecordsResponse getRecords(GetMyRecords command);
    GetMyRecordContentsResponse getRecordContents(GetRecordContents command);
    GetContinuingRecordResponse getContinuingRecord(GetContinuingRecord command);
    CompleteRecordResponse completeRecord(CompleteRecord command);
    ContinueRecordResponse continueRecord(ContinueRecord command);
    DeleteRecordResponse deleteRecord(DeleteRecord command);

}
