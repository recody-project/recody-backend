package com.recody.recodybackend.users.features.signin.normalsignin;

import com.recody.recodybackend.users.RecodyUserEmail;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInUser {
    
    private RecodyUserEmail email;
    private String password;
    
    private String userAgent;
    
    @Override
    public String toString() {
        return "{\"SignInUser\":{"
               + "\"email\":" + email
               + ", \"password\":" + ((password != null) ? ("\"" + password + "\"") : null)
               + "}}";
    }
}
