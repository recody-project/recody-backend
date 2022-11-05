package com.recody.recodybackend.record.features.continuerecord;

import com.recody.recodybackend.record.RecordEvent;
import com.recody.recodybackend.catalog.data.record.RecordEntity;
import com.recody.recodybackend.catalog.data.record.RecordRepository;
import com.recody.recodybackend.record.exceptions.RecordNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultContinueRecordHandler implements ContinueRecordHandler {
    
    private final RecordRepository recordRepository;
    
    @Override
    @Transactional
    public List<RecordEvent> handle(ContinueRecord command) {
        String recordId = command.getRecordId();
        Optional<RecordEntity> optionalRecord = recordRepository.findByRecordId(recordId);
        if (optionalRecord.isEmpty()) {
            throw new RecordNotFound();
        }
        RecordEntity recordEntity = optionalRecord.get();
        ArrayList<RecordEvent> events = new ArrayList<>();
        
        recordEntity.setCompleted(false);
        events.add(RecordEvent.RecordContinuing);
        if (!recordEntity.getNote().equals(command.getNote())) {
            recordEntity.setNote(command.getNote());
            events.add(RecordEvent.NoteUpdated);
        }
        if (!recordEntity.getTitle().equals(command.getTitle())){
            recordEntity.setTitle(command.getTitle());
            events.add(RecordEvent.TitleUpdated);
        }
        log.debug("record updated. events: {}", events);
        return events;
    }
}
