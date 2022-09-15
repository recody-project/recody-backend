package com.recody.recodybackend.users.features.login.googlelogin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RefreshGoogleAccessToken {
    
    private String resourceRefreshToken;
}
