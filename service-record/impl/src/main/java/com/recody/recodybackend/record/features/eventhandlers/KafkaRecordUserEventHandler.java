package com.recody.recodybackend.record.features.eventhandlers;

import com.recody.recodybackend.common.events.RecodyTopics;
import com.recody.recodybackend.record.data.users.RecordUserEntity;
import com.recody.recodybackend.record.data.users.RecordUserRepository;
import com.recody.recodybackend.users.events.UserCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener( topics = RecodyTopics.USER, groupId = "record" )
class KafkaRecordUserEventHandler implements RecordUserEventHandler {
    
    private final RecordUserRepository recordUserRepository;
    
    @Override
    @KafkaHandler
    public void on(@Header( name = KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) String key,
                   @Payload UserCreated event) {
        log.debug( "consuming event: {}", event );
        RecordUserEntity entity = RecordUserEntity.builder()
                                                  .userId( event.getUserId() )
                                                  .email( event.getEmail() )
                                                  .role( event.getRole() ).build();
        RecordUserEntity saved = recordUserRepository.save( entity );
        log.info( "Record 서비스에 유저를 동기화하였습니다.: {}", saved );
    }
}
