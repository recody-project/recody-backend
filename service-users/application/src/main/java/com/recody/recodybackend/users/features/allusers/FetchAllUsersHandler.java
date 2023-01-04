package com.recody.recodybackend.users.features.allusers;

import com.recody.recodybackend.users.events.UserCreated;

import java.util.List;

public interface FetchAllUsersHandler {
    
    List<UserCreated> handle(FetchAllUsers command);
    
}
