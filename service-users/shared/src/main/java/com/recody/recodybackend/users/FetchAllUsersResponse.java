package com.recody.recodybackend.users;

import com.recody.recodybackend.users.events.UserCreated;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FetchAllUsersResponse {
    
    List<UserCreated> users;
    
}
