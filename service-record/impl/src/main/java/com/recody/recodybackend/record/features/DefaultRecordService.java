package com.recody.recodybackend.record.features;

import com.recody.recodybackend.record.Record;
import com.recody.recodybackend.record.RecordEvent;
import com.recody.recodybackend.record.features.addrecord.AddRecord;
import com.recody.recodybackend.record.features.addrecord.AddRecordHandler;
import com.recody.recodybackend.record.features.completerecord.CompleteRecord;
import com.recody.recodybackend.record.features.completerecord.CompleteRecordHandler;
import com.recody.recodybackend.record.features.continuerecord.ContinueRecord;
import com.recody.recodybackend.record.features.continuerecord.ContinueRecordHandler;
import com.recody.recodybackend.record.features.deleterecord.DeleteRecord;
import com.recody.recodybackend.record.features.deleterecord.DeleteRecordHandler;
import com.recody.recodybackend.record.features.getcontinuingrecord.GetContinuingRecord;
import com.recody.recodybackend.record.features.getcontinuingrecord.GetContinuingRecordHandler;
import com.recody.recodybackend.record.features.getmyrecords.GetMyRecords;
import com.recody.recodybackend.record.features.getmyrecords.GetMyRecordsHandler;
import com.recody.recodybackend.record.features.getrecord.GetRecord;
import com.recody.recodybackend.record.features.getrecord.GetRecordHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class DefaultRecordService implements RecordService{
    
    private final AddRecordHandler addRecordHandler;
    private final GetRecordHandler getRecordHandler;
    private final GetMyRecordsHandler getMyRecordsHandler;
    private final CompleteRecordHandler completeRecordHandler;
    private final ContinueRecordHandler continueRecordHandler;
    private final GetContinuingRecordHandler getContinuingRecordHandler;
    
    private final DeleteRecordHandler deleteRecordHandler;
    
    @Override
    public AddRecordResponse addRecord(AddRecord command) {
        String recordId = addRecordHandler.handle(command);
        log.debug("Added Record. recordId: {}", recordId);
        return new AddRecordResponse(recordId);
    }
    
    @Override
    public GetRecordResponse getRecord(GetRecord command) {
        Record record = getRecordHandler.handle(command);
        log.debug("Got Record. record: {}", record);
        return new GetRecordResponse(record);
    }
    
    @Override
    public GetRecordsResponse getRecords(GetMyRecords command) {
        List<Record> records = getMyRecordsHandler.handle(command);
        int size = records.size();
        log.debug("Got {} Records", size);
        return new GetRecordsResponse(records, size);
    }
    
    @Override
    public GetContinuingRecordResponse getContinuingRecord(GetContinuingRecord command) {
        Record record = getContinuingRecordHandler.handle(command);
        log.debug("User {}'s most recent continuing record: {}", command.getUserId(), record);
        return new GetContinuingRecordResponse(record);
    }
    
    @Override
    public CompleteRecordResponse completeRecord(CompleteRecord command) {
        boolean completed = completeRecordHandler.handle(command);
        return CompleteRecordResponse.builder().recordId(command.getRecordId()).completed(completed).build();
    }
    
    @Override
    public ContinueRecordResponse continueRecord(ContinueRecord command) {
        List<RecordEvent> events = continueRecordHandler.handle(command);
        log.debug("record continuing");
        return ContinueRecordResponse.builder()
                                     .recordId(command.getRecordId())
                                     .events(events)
                                     .build();
    }
    
    @Override
    public DeleteRecordResponse deleteRecord(DeleteRecord command) {
        return new DeleteRecordResponse(deleteRecordHandler.handle(command));
    }
    
}
