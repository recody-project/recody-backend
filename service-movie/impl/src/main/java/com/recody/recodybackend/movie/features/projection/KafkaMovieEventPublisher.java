package com.recody.recodybackend.movie.features.projection;

import com.recody.recodybackend.common.events.LoggingCallback;
import com.recody.recodybackend.common.events.RecodyTopics;
import com.recody.recodybackend.movie.events.MovieCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class KafkaMovieEventPublisher implements MovieEventPublisher{
    
    private final KafkaTemplate<String, MovieCreated> movieCreatedKafkaTemplate;
    
    @Override
    public void publish(MovieCreated event) {
        movieCreatedKafkaTemplate.send( RecodyTopics.CONTENT_MANAGEMENT, event )
                .addCallback( new LoggingCallback() );
    }
}
