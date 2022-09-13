package com.recody.recodybackend.users.features.login;

import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class GetUserInfo {
    private String accessToken;
    
    @Override
    public String toString() {
        return "{\"GetUserInfo\":{" + "\"accessToken\":" + ((accessToken != null) ? ("\"" + accessToken + "\"") : null) + "}}";
    }
}
