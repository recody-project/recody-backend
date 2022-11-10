package com.recody.recodybackend.catalog.features.projection;

import com.recody.recodybackend.common.events.ContentCreated;
import com.recody.recodybackend.common.events.ContentRated;
import com.recody.recodybackend.common.events.LoggingCallback;
import com.recody.recodybackend.common.events.RecodyTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class KafkaContentEventPublisher implements ContentEventPublisher {
    
    private final KafkaTemplate<String, ContentCreated> contentCreatedTemplate;
    private final KafkaTemplate<String, ContentRated> ratedKafkaTemplate;
    
    @Override
    public void publish(ContentCreated event) {
        contentCreatedTemplate.send(RecodyTopics.CONTENT, event)
                              .addCallback(new LoggingCallback() );
    }
    
    @Override
    public void publish(ContentRated event) {
        ratedKafkaTemplate.send(RecodyTopics.CONTENT, event)
                          .addCallback(new LoggingCallback());
    }
}
