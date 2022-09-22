package com.recody.recodybackend.record.features.getmyrecords;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.record.Record;
import com.recody.recodybackend.record.data.RecordEntity;
import com.recody.recodybackend.record.data.RecordRepository;
import com.recody.recodybackend.record.exceptions.RecordErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetMyRecordsHandler implements GetMyRecordsHandler{
    
    private final RecordRepository recordRepository;
    
    @Override
    public List<Record> handle(GetMyRecords command) {
        Long userId = command.getUserId();
        Optional<List<RecordEntity>> optionalRecords = recordRepository.findAllByUserId(userId);
        
        if (optionalRecords.isEmpty()){
            throw new ApplicationException(RecordErrorType.NoRecordFound, HttpStatus.NOT_FOUND);
        }
        List<RecordEntity> recordEntities = optionalRecords.get();
        ArrayList<Record> records = new ArrayList<>();
    
        for (RecordEntity recordEntity : recordEntities) {
            records.add(Record.builder()
                              .recordId(recordEntity.getRecordId())
                                .userId(recordEntity.getUserId())
                                .note(recordEntity.getNote())
                                .contentId(recordEntity.getContentId())
                              .build());
        }
        log.debug("{} records found", records.size());
        return records;
    }
}
