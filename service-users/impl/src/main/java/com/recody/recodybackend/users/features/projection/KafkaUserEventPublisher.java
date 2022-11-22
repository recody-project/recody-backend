package com.recody.recodybackend.users.features.projection;

import com.recody.recodybackend.common.KafkaEventProcessingStrategy;
import com.recody.recodybackend.common.events.LoggingCallback;
import com.recody.recodybackend.common.events.RecodyTopics;
import com.recody.recodybackend.users.events.UserCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Conditional(value = KafkaEventProcessingStrategy.class)
@RequiredArgsConstructor
@Slf4j
class KafkaUserEventPublisher implements UserEventPublisher{
    
    private final KafkaTemplate<String, UserCreated> userCreatedKafkaTemplate;
    
    @Override
    public void publish(UserCreated event) {
        userCreatedKafkaTemplate.send( RecodyTopics.USER, event )
                .addCallback( new LoggingCallback() );
    }
}
