package com.recody.recodybackend.users.features.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateRefreshToken {
    private Long userId;
    private String email;
    
    @Override
    public String toString() {
        return "{\"CreateRefreshToken\":{" + "\"userId\":" + userId + ",\"email\":" + ((email != null) ? ("\"" + email + "\"") : null) + "}}";
    }
}
