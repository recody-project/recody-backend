package com.recody.recodybackend.users.features.login;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SocialLoginResponse {
    private String accessToken;
    private String refreshToken;
    private String accessExpireTime;
    private String refreshExpireTime;
    
    @Override
    public String toString() {
        return "{\"SocialLoginResponse\":{" + "\"accessToken\":" + ((accessToken != null) ? ("\"" + accessToken + "\"") : null) + ", \"refreshToken\":" + ((refreshToken != null) ? ("\"" + refreshToken + "\"") : null) + ", \"accessExpireTime\":" + ((accessExpireTime != null) ? ("\"" + accessExpireTime + "\"") : null) + ", \"refreshExpireTime\":" + ((refreshExpireTime != null) ? ("\"" + refreshExpireTime + "\"") : null) + "}}";
    }
}
