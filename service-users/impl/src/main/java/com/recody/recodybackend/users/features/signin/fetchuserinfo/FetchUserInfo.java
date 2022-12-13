package com.recody.recodybackend.users.features.signin.fetchuserinfo;

import com.recody.recodybackend.users.sociallogin.ResourceAccessToken;
import com.recody.recodybackend.users.sociallogin.ResourceRefreshToken;
import com.recody.recodybackend.users.SocialProvider;
import lombok.*;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FetchUserInfo {
    
    private ResourceAccessToken resourceAccessToken;
    private ResourceRefreshToken resourceRefreshToken;
    private SocialProvider socialProvider;
    
}
