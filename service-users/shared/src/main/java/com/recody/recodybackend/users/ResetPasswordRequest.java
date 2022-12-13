package com.recody.recodybackend.users;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResetPasswordRequest {
    
    private String email;
    private String password;
    private String passwordConfirm;
    private String verificationCode;
    
    @Override
    public String toString() {
        return "{\"ResetPasswordRequest\":{"
               + "\"password\":" + ((password != null) ? ("\"" + password + "\"") : null)
               + ", \"passwordConfirm\":" + ((passwordConfirm != null) ? ("\"" + passwordConfirm + "\"") : null)
               + ", \"verificationCode\":" + ((verificationCode != null) ? ("\"" + verificationCode + "\"") : null)
               + "}}";
    }
}
