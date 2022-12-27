package com.recody.recodybackend.catalog.features.projection;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.common.events.ContentRated;
import com.recody.recodybackend.event.ContentCreated;
import org.springframework.scheduling.annotation.Async;

public interface ContentEventPublisher {
    
    @Async( Recody.CATALOG_TASK_EXECUTOR )
    void publish(ContentCreated event);
    @Async( Recody.CATALOG_TASK_EXECUTOR )
    void publish(ContentRated event);
    
}
