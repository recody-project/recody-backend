package com.recody.recodybackend.users.web;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInUserRequest {
    
    @NotNull
    private String email;
    @NotNull
    private String password;
    
}
