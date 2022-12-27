package com.recody.recodybackend.movie.features.projection;

import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.movie.events.MovieCreated;
import org.springframework.scheduling.annotation.Async;

public interface MovieEventPublisher {
    
    @Async( Recody.MOVIE_TASK_EXECUTOR )
    void publish(MovieCreated event);
    
}
