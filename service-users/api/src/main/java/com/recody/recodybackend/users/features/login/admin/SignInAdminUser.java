package com.recody.recodybackend.users.features.login.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInAdminUser {
    
    private String username;
    private String password;
}
