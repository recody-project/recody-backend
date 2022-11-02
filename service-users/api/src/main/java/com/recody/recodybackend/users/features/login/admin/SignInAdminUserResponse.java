package com.recody.recodybackend.users.features.login.admin;

import com.recody.recodybackend.users.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInAdminUserResponse {
    
    private Role role;
    private String accessToken;
    private String refreshToken;
    private String accessExpireTime;
    private String refreshExpireTime;
    
}
