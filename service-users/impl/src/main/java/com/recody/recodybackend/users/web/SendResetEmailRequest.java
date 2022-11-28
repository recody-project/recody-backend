package com.recody.recodybackend.users.web;

import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendResetEmailRequest {
    
    @Email
    private String email;
    
}
