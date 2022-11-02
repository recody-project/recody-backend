package com.recody.recodybackend.users.features.login.googlelogin;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.recody.recodybackend.users.OAuthUserInfo;
import com.recody.recodybackend.users.SocialProvider;
import lombok.*;

/*
* google 리소스 서버로 부터 받은 유저 정보.*/
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleOAuthUserInfo implements OAuthUserInfo {
    private String id;
    private String email;
    @JsonAlias("verified_email")
    private String verifiedEmail;
    private String name;
    @JsonAlias("given_name")
    private String givenName;
    @JsonAlias("family_name")
    private String familyName;
    private String picture;
    private String locale;
    
    @Builder.Default
    private SocialProvider socialProvider = SocialProvider.GOOGLE;
    
    @Override
    public String getProfileImageUrl() {
        return picture;
    }
}
