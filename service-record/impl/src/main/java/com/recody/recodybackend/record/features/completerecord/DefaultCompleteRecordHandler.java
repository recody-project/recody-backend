package com.recody.recodybackend.record.features.completerecord;

import com.recody.recodybackend.record.data.record.RecordEntity;
import com.recody.recodybackend.record.data.record.RecordRepository;
import com.recody.recodybackend.record.exceptions.RecordNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class DefaultCompleteRecordHandler implements CompleteRecordHandler{
    
    private final RecordRepository recordRepository;
    
    @Override
    @Transactional
    public boolean handle(CompleteRecord command) {
        Optional<RecordEntity> optionalRecord = recordRepository.findByRecordId(command.getRecordId());
        if (optionalRecord.isEmpty()){
            throw new RecordNotFound();
        }
        RecordEntity recordEntity = optionalRecord.get();
        if (command.getTitle() != null) {
            recordEntity.setTitle(command.getTitle());
        }
        if (command.getNote() != null) {
            recordEntity.setNote(command.getNote());
        }
        recordEntity.setCompleted(true);
        return recordEntity.isCompleted();
    }
}
