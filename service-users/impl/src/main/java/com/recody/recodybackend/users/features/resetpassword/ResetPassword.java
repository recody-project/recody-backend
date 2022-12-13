package com.recody.recodybackend.users.features.resetpassword;

import com.recody.recodybackend.users.VerificationCode;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResetPassword {
    
    private String email;
    private String password;
    private String passwordConfirm;
    private VerificationCode verificationCode;
    
    @Override
    public String toString() {
        return "{\"ResetPassword\":{"
               + "\"password\":" + ((password != null) ? ("\"" + password + "\"") : null)
               + ", \"passwordConfirm\":" + ((passwordConfirm != null) ? ("\"" + passwordConfirm + "\"") : null)
               + ", \"verificationCode\":" + verificationCode
               + "}}";
    }
}
