package com.recody.recodybackend.users.web;

import com.recody.recodybackend.users.RecodySignInSession;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInUserResponse {
    
    private RecodySignInSession signInInfo;
    
}
