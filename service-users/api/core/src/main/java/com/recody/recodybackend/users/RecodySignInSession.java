package com.recody.recodybackend.users;

import lombok.*;

/**
 * accessToken, RefreshToken 을 포함하는 정보입니다. */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecodySignInSession {
    
    private SocialProvider socialType;
    
    private Role role;
    private String accessToken;
    private String refreshToken;
    private String accessExpireTime;
    private String refreshExpireTime;
    
    @Override
    public String toString() {
        return "{\"RecodySignInSession\":{"
               + "\"socialType\":" + ((socialType != null) ? ("\"" + socialType + "\"") : null)
               + ", \"role\":" + ((role != null) ? ("\"" + role + "\"") : null)
               + ", \"accessToken\":" + ((accessToken != null) ? ("\"" + accessToken + "\"") : null)
               + ", \"refreshToken\":" + ((refreshToken != null) ? ("\"" + refreshToken + "\"") : null)
               + ", \"accessExpireTime\":" + ((accessExpireTime != null) ? ("\"" + accessExpireTime + "\"") : null)
               + ", \"refreshExpireTime\":" + ((refreshExpireTime != null) ? ("\"" + refreshExpireTime + "\"") : null)
               + "}}";
    }
}
