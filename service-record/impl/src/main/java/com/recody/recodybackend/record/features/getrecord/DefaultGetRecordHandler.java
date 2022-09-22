package com.recody.recodybackend.record.features.getrecord;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.record.Record;
import com.recody.recodybackend.record.data.RecordEntity;
import com.recody.recodybackend.record.data.RecordRepository;
import com.recody.recodybackend.record.exceptions.RecordErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetRecordHandler implements GetRecordHandler {
    
    private final RecordRepository recordRepository;
    
    @Override
    public Record handle(GetRecord command) {
        log.debug("handling command: {}", command);
        String recordId = command.getRecordId();
        Optional<RecordEntity> optionalRecord = recordRepository.findByRecordId(recordId);
        if (optionalRecord.isEmpty()) {
            log.debug("No Record Found: {}", optionalRecord);
            throw new ApplicationException(RecordErrorType.NoRecordFound, HttpStatus.NOT_FOUND);
        }
        RecordEntity recordEntity = optionalRecord.get();
        Record record = createRecord(recordEntity);
        log.debug("Returning record: {}", record);
        return record;
    }
    
    private Record createRecord(RecordEntity recordEntity) {
        return Record
                .builder()
                .recordId(recordEntity.getRecordId())
                .contentId(recordEntity.getContentId())
                .note(recordEntity.getNote())
                .userId(recordEntity.getUserId())
                .build();
    }
}
