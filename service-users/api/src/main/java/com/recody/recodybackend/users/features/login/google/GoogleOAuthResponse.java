package com.recody.recodybackend.users.features.login.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/*
* google 리소스 서버로 부터 받은 유저 정보.*/
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class GoogleOAuthResponse {
    private String id;
    private String email;
    private String verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;
}
