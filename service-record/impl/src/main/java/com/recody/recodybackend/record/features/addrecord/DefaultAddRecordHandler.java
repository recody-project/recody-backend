package com.recody.recodybackend.record.features.addrecord;

import com.recody.recodybackend.record.data.RecordEntity;
import com.recody.recodybackend.record.data.RecordRepository;
import com.recody.recodybackend.record.exceptions.RecordAlreadyExists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultAddRecordHandler implements AddRecordHandler{
    
    private final RecordRepository recordRepository;
    
    @Override
    @Transactional
    public String handle(AddRecord command) {
        log.debug("handling command: {}", command);
        Optional<List<RecordEntity>> optionalRecords = recordRepository.findAllByUserIdAndContentId(command.getUserId(),
                                                                                                    command.getContentId());
        if (optionalRecords.isPresent()) {
            if (!optionalRecords.get().isEmpty()) {
                throw new RecordAlreadyExists();
            }
        }
        
        RecordEntity savedRecord = saveFirstRecord(command);
        return savedRecord.getRecordId();
    }
    
    private RecordEntity saveFirstRecord(AddRecord command) {
        RecordEntity recordEntity = RecordEntity
                .builder()
                .userId(command.getUserId())
                .title(command.getTitle())
                .note(command.getNote())
                .contentId(command.getContentId())
                .appreciationDate(command.getAppreciationDate())
                .build();
        return recordRepository.save(recordEntity);
    }
}
