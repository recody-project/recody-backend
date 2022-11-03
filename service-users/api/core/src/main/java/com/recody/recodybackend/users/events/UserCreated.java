package com.recody.recodybackend.users.events;

import com.recody.recodybackend.users.Role;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreated {
    
    private Long userId;
    private Role role;
    private String email;
    
    @Override
    public String toString() {
        return "{\"UserCreated\":{"
               + "\"userId\":" + userId
               + ", \"role\":" + ((role != null) ? ("\"" + role + "\"") : null)
               + ", \"email\":" + ((email != null) ? ("\"" + email + "\"") : null)
               + "}}";
    }
}
