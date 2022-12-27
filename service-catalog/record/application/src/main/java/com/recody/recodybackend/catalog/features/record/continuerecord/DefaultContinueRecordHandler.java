package com.recody.recodybackend.catalog.features.record.continuerecord;

import com.recody.recodybackend.catalog.data.record.RecordEntity;
import com.recody.recodybackend.catalog.data.record.RecordRepository;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserRepository;
import com.recody.recodybackend.exceptions.RecordNotFound;
import com.recody.recodybackend.record.RecordEvent;
import com.recody.recodybackend.users.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultContinueRecordHandler implements ContinueRecordHandler {
    
    private final RecordRepository recordRepository;
    
    private final CatalogUserRepository userRepository;
    
    @Override
    @Transactional
    public List<RecordEvent> handle(ContinueRecord command) {
        log.debug( "handling command: {}", command );
        CatalogUserEntity userEntity = userRepository.findById( command.getUserId() ).orElseThrow( UserNotFoundException::new );
        String recordId = command.getRecordId();
        RecordEntity recordEntity = recordRepository.findByRecordIdAndUser( recordId, userEntity ).orElseThrow( RecordNotFound::new );
        ArrayList<RecordEvent> events = new ArrayList<>();
        recordEntity.setCompleted(false);
        events.add(RecordEvent.RecordContinuing);
        log.debug("record updated. events: {}", events);
        return events;
    }
}
