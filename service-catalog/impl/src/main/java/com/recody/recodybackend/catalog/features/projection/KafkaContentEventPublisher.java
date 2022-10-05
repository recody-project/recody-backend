package com.recody.recodybackend.catalog.features.projection;

import com.recody.recodybackend.common.events.ContentCreated;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaContentEventPublisher implements ContentEventPublisher {
    
    private final KafkaTemplate<String, ContentCreated> contentCreatedTemplate;
    private final String contentTopic = "content";
    
    @Override
    public void publish(ContentCreated event) {
        contentCreatedTemplate.send(contentTopic, event);
    }
}
