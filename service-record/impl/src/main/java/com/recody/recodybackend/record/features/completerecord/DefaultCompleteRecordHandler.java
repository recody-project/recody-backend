package com.recody.recodybackend.record.features.completerecord;

import com.recody.recodybackend.record.data.RecordEntity;
import com.recody.recodybackend.record.data.RecordRepository;
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
        recordEntity.setNote(command.getNote());
        recordEntity.setCompleted(true);
        return recordEntity.isCompleted();
    }
}
