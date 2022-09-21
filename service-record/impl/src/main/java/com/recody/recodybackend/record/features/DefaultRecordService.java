package com.recody.recodybackend.record.features;

import com.recody.recodybackend.record.Record;
import com.recody.recodybackend.record.features.addrecord.AddRecord;
import com.recody.recodybackend.record.features.addrecord.AddRecordHandler;
import com.recody.recodybackend.record.features.getrecord.GetRecord;
import com.recody.recodybackend.record.features.getrecord.GetRecordHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class DefaultRecordService implements RecordService{
    
    private final AddRecordHandler addRecordHandler;
    private final GetRecordHandler getRecordHandler;
    
    
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
    
    
}
