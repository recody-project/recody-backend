package com.recody.recodybackend.users;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class SocialLoginRequest {
    @NotNull(message = "${notNull}")
    private String resourceAccessToken;
    
    private String resourceRefreshToken;
    
    @Override
    public String toString() {
        return "{\"SocialLoginRequest\":{" + "\"resourceAccessToken\":" + ((resourceAccessToken != null) ? ("\"" + resourceAccessToken + "\"") : null) + ", \"resourceRefreshToken\":" + ((resourceRefreshToken != null) ? ("\"" + resourceRefreshToken + "\"") : null) + "}}";
    }
}
