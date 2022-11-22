package com.recody.recodybackend.users.features.projection;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.common.SpringEventProcessingStrategy;
import com.recody.recodybackend.users.events.UserCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Conditional(value = SpringEventProcessingStrategy.class )
@RequiredArgsConstructor
@Slf4j
class SpringUserEventPublisher implements UserEventPublisher{

    private final ApplicationEventPublisher publisher;

    @Override
    @Async( Recody.USERS_TASK_EXECUTOR )
    public void publish(UserCreated event) {
        publisher.publishEvent( event );
        log.info( "event published [spring]: {}", event );
    }
}
