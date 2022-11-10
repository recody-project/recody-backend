package com.recody.recodybackend.movie.features.projection;

import com.recody.recodybackend.movie.events.MovieCreated;

public interface MovieEventPublisher {
    
    void publish(MovieCreated event);
    
}
