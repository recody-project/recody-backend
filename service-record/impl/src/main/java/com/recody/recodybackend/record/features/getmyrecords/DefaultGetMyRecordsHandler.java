package com.recody.recodybackend.record.features.getmyrecords;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.common.exceptions.InternalServerError;
import com.recody.recodybackend.record.Record;
import com.recody.recodybackend.record.data.content.RecordContentRepository;
import com.recody.recodybackend.record.data.record.RecordEntity;
import com.recody.recodybackend.record.data.record.RecordMapper;
import com.recody.recodybackend.record.data.record.RecordRepository;
import com.recody.recodybackend.record.exceptions.RecordErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetMyRecordsHandler implements GetMyRecordsHandler{
    
    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;
    
    private final RecordContentRepository contentRepository;
    
    @Override
    @Transactional
    public List<Record> handle(GetMyRecords command) {
        log.debug("handling command: {}", command);
        Long userId = command.getUserId();
        PageRequest pageable = PageRequest.of(command.getPage(), command.getSize());
        Optional<List<RecordEntity>> optionalRecords = recordRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        // repository 는 항상 List 를 반환해야 한다.
        optionalRecords.orElseThrow(InternalServerError::new);
    
        if (optionalRecords.get().isEmpty()){
            throw new ApplicationException(RecordErrorType.NoRecordFound, HttpStatus.NOT_FOUND);
        }
        
        List<RecordEntity> recordEntities = optionalRecords.get();
        ArrayList<Record> records = new ArrayList<>();
        for (RecordEntity recordEntity : recordEntities) {
            Record record = recordMapper.map(recordEntity);
            records.add(record);
        }
        log.debug("{} records found", records.size());
        return records;
    }
}
