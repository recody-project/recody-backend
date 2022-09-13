package com.recody.recodybackend.users.features.login;

import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class GetUserInfoFromResourceServer {
    
    private String resourceAccessToken;
    
    @Override
    public String toString() {
        return "{\"GetUserInfo\":{" + "\"accessToken\":" + ((resourceAccessToken != null) ? ("\"" + resourceAccessToken + "\"") : null) + "}}";
    }
}
