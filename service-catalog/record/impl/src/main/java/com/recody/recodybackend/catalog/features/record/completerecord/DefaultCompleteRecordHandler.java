package com.recody.recodybackend.catalog.features.record.completerecord;

import com.recody.recodybackend.catalog.data.record.RecordEntity;
import com.recody.recodybackend.catalog.data.record.RecordRepository;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserRepository;
import com.recody.recodybackend.exceptions.RecordNotFound;
import com.recody.recodybackend.users.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultCompleteRecordHandler implements CompleteRecordHandler{
    
    private final RecordRepository recordRepository;
    
    private final CatalogUserRepository userRepository;
    
    @Override
    @Transactional
    public boolean handle(CompleteRecord command) {
        log.debug( "hangling command: {}", command );
        CatalogUserEntity userEntity = userRepository.findById( command.getUserId() )
                                                            .orElseThrow( UserNotFoundException::new );
        RecordEntity recordEntity = recordRepository.findByRecordIdAndUser( command.getRecordId(), userEntity )
                                                     .orElseThrow( RecordNotFound::new );
        recordEntity.setCompleted(true);
        return recordEntity.isCompleted();
    }
}
