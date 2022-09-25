package com.recody.recodybackend.record.features.getcontinuingrecord;

import com.recody.recodybackend.record.Record;
import com.recody.recodybackend.record.data.RecordEntity;
import com.recody.recodybackend.record.data.RecordRepository;
import com.recody.recodybackend.record.exceptions.RecordNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetContinuingRecordHandler implements GetContinuingRecordHandler{
    
    private final RecordRepository recordRepository;
    
    @Override
    @Transactional
    public Record handle(GetContinuingRecord command) {
        Long userId = command.getUserId();
        Optional<RecordEntity> optionalRecordEntity = recordRepository.findFirstByUserIdAndCompletedIsFalseOrderByLastModifiedAtDesc(userId);
        if (optionalRecordEntity.isEmpty()){
            throw new RecordNotFound();
        }
        RecordEntity recordEntity = optionalRecordEntity.get();
        Record record = createRecord(recordEntity);
        log.debug("record: {}", record);
        return record;
    }
    
    private Record createRecord(RecordEntity recordEntity) {
        return Record
                .builder()
                .recordId(recordEntity.getRecordId())
                .contentId(recordEntity.getContentId())
                .title(recordEntity.getTitle())
                .note(recordEntity.getNote())
                .userId(recordEntity.getUserId())
                .completed(recordEntity.isCompleted())
                .build();
    }
}
