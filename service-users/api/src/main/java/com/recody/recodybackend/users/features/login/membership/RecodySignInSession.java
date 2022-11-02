package com.recody.recodybackend.users.features.login.membership;

import com.recody.recodybackend.users.SocialProvider;
import lombok.Builder;
import lombok.Data;

/**
 * accessToken, RefreshToken 을 포함하는 정보입니다. */

@Builder
@Data
public class RecodySignInSession {
    
    private SocialProvider socialType;
    private String accessToken;
    private String refreshToken;
    private String accessExpireTime;
    private String refreshExpireTime;
    
    @Override
    public String toString() {
        return "{\"RecodySignInResponse\":{" + "\"socialType\":" + ((socialType != null) ? ("\"" + socialType + "\"") : null) + ", \"accessToken\":" + ((accessToken != null) ? ("\"" + accessToken + "\"") : null) + ", \"refreshToken\":" + ((refreshToken != null) ? ("\"" + refreshToken + "\"") : null) + ", \"accessExpireTime\":" + ((accessExpireTime != null) ? ("\"" + accessExpireTime + "\"") : null) + ", \"refreshExpireTime\":" + ((refreshExpireTime != null) ? ("\"" + refreshExpireTime + "\"") : null) + "}}";
    }
}
