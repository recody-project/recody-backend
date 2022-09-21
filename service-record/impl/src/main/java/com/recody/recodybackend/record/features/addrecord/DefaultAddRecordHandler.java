package com.recody.recodybackend.record.features.addrecord;

import com.recody.recodybackend.record.data.RecordEntity;
import com.recody.recodybackend.record.data.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DefaultAddRecordHandler implements AddRecordHandler{
    
    private final RecordRepository recordRepository;
    
    @Override
    public String handle(AddRecord command) {
        RecordEntity recordEntity = RecordEntity
                .builder()
                .userId(command.getUserId())
                .note(command.getNote())
                .contentId(command.getContentId())
                .build();
        RecordEntity savedRecord = recordRepository.save(recordEntity);
        return savedRecord.getRecordId();
    }
}
