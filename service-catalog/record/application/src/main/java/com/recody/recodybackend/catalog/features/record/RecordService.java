package com.recody.recodybackend.catalog.features.record;

import com.recody.recodybackend.catalog.features.record.addrecord.AddRecord;
import com.recody.recodybackend.catalog.features.record.completerecord.CompleteRecord;
import com.recody.recodybackend.catalog.features.record.continuerecord.ContinueRecord;
import com.recody.recodybackend.catalog.features.record.deleterecord.DeleteRecord;
import com.recody.recodybackend.catalog.features.record.getcontinuingrecord.GetContinuingRecord;
import com.recody.recodybackend.catalog.features.record.getmyrecords.GetMyRecords;
import com.recody.recodybackend.catalog.features.record.getrecord.GetRecord;
import com.recody.recodybackend.catalog.features.record.getrecordcontent.GetContinuingRecordContent;
import com.recody.recodybackend.catalog.features.record.getrecordcontents.GetRecordContents;
import com.recody.recodybackend.record.web.*;

public interface RecordService {
    
    AddRecordResponse addRecord(AddRecord command);
    GetRecordResponse getRecord(GetRecord command);
    GetRecordsResponse getRecords(GetMyRecords command);
    GetMyRecordContentsResponse getRecordContents(GetRecordContents command);
    GetContinuingRecordContentResponse getContinuingRecordContent(GetContinuingRecordContent command);
    GetContinuingRecordResponse getContinuingRecord(GetContinuingRecord command);
    CompleteRecordResponse completeRecord(CompleteRecord command);
    ContinueRecordResponse continueRecord(ContinueRecord command);
    DeleteRecordResponse deleteRecord(DeleteRecord command);
}
