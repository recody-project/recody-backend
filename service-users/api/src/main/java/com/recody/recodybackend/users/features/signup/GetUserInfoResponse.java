package com.recody.recodybackend.users.features.signup;

import com.recody.recodybackend.users.RecodyUserInfo;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUserInfoResponse {
    
    private RecodyUserInfo userInfo;
    
}
