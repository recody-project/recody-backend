package com.recody.recodybackend.users.features.login.admin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInAdminUser {
    
    private String username;
    private String password;
}
