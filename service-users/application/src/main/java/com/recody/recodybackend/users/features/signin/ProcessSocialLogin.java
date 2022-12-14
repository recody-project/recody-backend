package com.recody.recodybackend.users.features.signin;

import lombok.*;

/*
* social access token 과 userAgent 정보를 사용해 로그인을 진행한다. */
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class ProcessSocialLogin {
    private String resourceAccessToken;
    private String resourceRefreshToken;
    private String userAgent;
    
    @Override
    public String toString() {
        return "{\"ProcessLogin\":{" + "\"accessToken\":" + ((resourceAccessToken != null) ? ("\"" + resourceAccessToken + "\"") : null) + ", \"userAgent\":" + ((userAgent != null) ? ("\"" + userAgent + "\"") : null) + "}}";
    }
}
