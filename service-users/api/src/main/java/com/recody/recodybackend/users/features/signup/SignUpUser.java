package com.recody.recodybackend.users.features.signup;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpUser {
    
    private String username;
    private String password;
    private String email;
    private String nickname;
}
