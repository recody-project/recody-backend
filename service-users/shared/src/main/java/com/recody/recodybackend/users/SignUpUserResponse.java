package com.recody.recodybackend.users;

import com.recody.recodybackend.users.RecodyUserInfo;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpUserResponse {
    
    private RecodyUserInfo userInfo;
    
}
