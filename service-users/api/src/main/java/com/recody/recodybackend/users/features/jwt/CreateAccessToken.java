package com.recody.recodybackend.users.features.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateAccessToken {
    private Long userId;
    private String email;
    
    @Override
    public String toString() {
        return "{\"CreateAccessToken\":{" + "\"userId\":" + userId + ", \"email\":" + ((email != null) ? ("\"" + email + "\"") : null) + "}}";
    }
}
