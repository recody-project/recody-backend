package com.recody.recodybackend.users.features.signup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpUser {
    
    private String username;
    private String password;
    private String email;
    private String nickname;
}
