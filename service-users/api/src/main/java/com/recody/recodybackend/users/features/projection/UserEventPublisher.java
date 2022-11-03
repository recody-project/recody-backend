package com.recody.recodybackend.users.features.projection;

import com.recody.recodybackend.users.events.UserCreated;

public interface UserEventPublisher {
    
    void publish(UserCreated event);
    
}
