package com.recody.recodybackend.users.features.login;

import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class SocialLoginRequest {
    private String socialAccessToken;
    
    @Override
    public String toString() {
        return "{\"SocialLoginRequest\":{" + "\"accessToken\":" + ((socialAccessToken != null) ? ("\"" + socialAccessToken + "\"") : null) + "}}";
    }
}
