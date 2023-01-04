package com.recody.recodybackend.catalog.features.eventhandlers;

import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserRepository;
import com.recody.recodybackend.common.OnKafkaEventProcessingStrategy;
import com.recody.recodybackend.common.events.RecodyTopics;
import com.recody.recodybackend.users.events.UserCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Conditional(value = OnKafkaEventProcessingStrategy.class )
@RequiredArgsConstructor
@Slf4j
@KafkaListener( topics = RecodyTopics.USER, groupId = "catalog" )
class KafkaCatalogUserEventHandler implements CatalogUserEventHandler {
    
    private final CatalogUserRepository catalogUserRepository;
    
    @Override
    @Transactional
    @KafkaHandler
    public void on(@Payload UserCreated event) {
        log.debug( "consuming event: {}", event );
        CatalogUserEntity entity = CatalogUserEntity.builder()
                                                    .id( event.getUserId() )
                                                    .role( event.getRole() )
                                                    .email( event.getEmail() )
                                                    .build();
        catalogUserRepository.save( entity );
        log.info( "Catalog 서비스에 유저가 동기화되었습니다.: {}", entity );
    }
}
