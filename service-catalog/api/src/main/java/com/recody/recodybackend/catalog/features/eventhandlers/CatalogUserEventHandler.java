package com.recody.recodybackend.catalog.features.eventhandlers;

import com.recody.recodybackend.users.events.UserCreated;

public interface CatalogUserEventHandler {
    
    void on(String key, UserCreated event);
    
}
