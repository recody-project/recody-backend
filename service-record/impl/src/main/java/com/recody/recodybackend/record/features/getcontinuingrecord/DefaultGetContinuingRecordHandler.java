package com.recody.recodybackend.record.features.getcontinuingrecord;

import com.recody.recodybackend.record.Record;
import com.recody.recodybackend.record.data.record.RecordEntity;
import com.recody.recodybackend.record.data.record.RecordMapper;
import com.recody.recodybackend.record.data.record.RecordRepository;
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
    private final RecordMapper recordMapper;
    
    @Override
    @Transactional
    public Record handle(GetContinuingRecord command) {
        Long userId = command.getUserId();
        Optional<RecordEntity> optionalRecordEntity = recordRepository.findFirstByUserIdAndCompletedIsFalseOrderByLastModifiedAtDesc(userId);
        if (optionalRecordEntity.isEmpty()){
            throw new RecordNotFound();
        }
        RecordEntity recordEntity = optionalRecordEntity.get();
        Record record = recordMapper.map(recordEntity);
        log.debug("record: {}", record);
        return record;
    }
}
