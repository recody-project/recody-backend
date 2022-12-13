package com.recody.recodybackend.book.features.projection;

import com.recody.recodybackend.book.events.BookCreated;
import com.recody.recodybackend.common.OnSpringEventProcessingStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Conditional(value = OnSpringEventProcessingStrategy.class)
public class SpringBookEventPublisher implements BookEventPublisher{

    private final ApplicationEventPublisher publisher;

    @Override
    public void publish(BookCreated event) {
        publisher.publishEvent(event);
        log.info( "published event [Spring]: {}", event );
    }
}
