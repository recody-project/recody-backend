package com.recody.recodybackend.record.features.addrecord;

import com.recody.recodybackend.record.data.RecordEntity;
import com.recody.recodybackend.record.data.RecordRepository;
import com.recody.recodybackend.record.exceptions.RecordAlreadyExists;
import jdk.jfr.TransitionTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class DefaultAddRecordHandler implements AddRecordHandler{
    
    private final RecordRepository recordRepository;
    
    @Override
    @Transactional
    public String handle(AddRecord command) {
        Optional<List<RecordEntity>> optionalRecords = recordRepository.findAllByUserIdAndContentId(command.getUserId(),
                                                                                                    command.getContentId());
        if (optionalRecords.isPresent()) {
            throw new RecordAlreadyExists();
        }
        
        RecordEntity savedRecord = saveFirstRecord(command);
        return savedRecord.getRecordId();
    }
    
    private RecordEntity saveFirstRecord(AddRecord command) {
        RecordEntity recordEntity = RecordEntity
                .builder()
                .userId(command.getUserId())
                .note(command.getNote())
                .contentId(command.getContentId())
                .build();
        return recordRepository.save(recordEntity);
    }
}
