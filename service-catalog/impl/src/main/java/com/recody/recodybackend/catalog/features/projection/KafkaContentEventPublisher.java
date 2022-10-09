package com.recody.recodybackend.catalog.features.projection;

import com.recody.recodybackend.common.events.ContentCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class KafkaContentEventPublisher implements ContentEventPublisher {
    
    private final KafkaTemplate<String, ContentCreated> contentCreatedTemplate;
    private final String contentTopic = "content";
    
    @Override
    public void publish(ContentCreated event) {
        contentCreatedTemplate.send(contentTopic, event);
        log.info("event published: {}", event);
    }
}
