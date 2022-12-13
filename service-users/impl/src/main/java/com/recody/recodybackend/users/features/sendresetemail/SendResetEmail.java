package com.recody.recodybackend.users.features.sendresetemail;

import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendResetEmail {
    
    @Email
    private String email;
    
    @Override
    public String toString() {
        return "{\"SendResetEmail\":{"
               + "\"email\":" + ((email != null) ? ("\"" + email + "\"") : null)
               + "}}";
    }
}
