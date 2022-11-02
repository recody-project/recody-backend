package com.recody.recodybackend.users.web;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpUserRequest {
    
    
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String passwordConfirm;
    
    private String name;
    private String nickname;
    private String pictureUrl;
    
    
    @Override
    public String toString() {
        return "{\"SignUpUserRequest\":{"
               + "\"email\":" + ((email != null) ? ("\"" + email + "\"") : null)
               + ", \"password\":" + ((password != null) ? ("\"" + password + "\"") : null)
               + ", \"passwordConfirm\":" + ((passwordConfirm != null) ? ("\"" + passwordConfirm + "\"") : null)
               + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null)
               + ", \"nickname\":" + ((nickname != null) ? ("\"" + nickname + "\"") : null)
               + ", \"pictureUrl\":" + ((pictureUrl != null) ? ("\"" + pictureUrl + "\"") : null)
               + "}}";
    }
}
