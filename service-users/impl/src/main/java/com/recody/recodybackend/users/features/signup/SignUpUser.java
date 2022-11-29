package com.recody.recodybackend.users.features.signup;

import com.recody.recodybackend.users.RecodyUserEmail;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpUser {
    @NonNull
    private RecodyUserEmail email;
    @NonNull
    private String password;
    @NonNull
    private String passwordConfirm;
    
    private String name;
    private String nickname;
    
    private String pictureUrl;
    
    @Override
    public String toString() {
        return "{\"SignUpUser\":{"
               + "\"email\":" + email
               + ", \"password\":" + ((password != null) ? ("\"" + password + "\"") : null)
               + ", \"passwordConfirm\":" + ((passwordConfirm != null) ? ("\"" + passwordConfirm + "\"") : null)
               + ", \"nickname\":" + ((nickname != null) ? ("\"" + nickname + "\"") : null)
               + "}}";
    }
}
