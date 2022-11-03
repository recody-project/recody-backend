package com.recody.recodybackend.record.features.eventhandlers;

import com.recody.recodybackend.users.events.UserCreated;

public interface RecordUserEventHandler {
    
    void on(String key, UserCreated event);
    
}
