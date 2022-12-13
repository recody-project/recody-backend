package com.recody.recodybackend.users.features.signin.naverlogin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class NaverOAuthResponse {
    private String resultcode;
    private String message;
    private NaverOAuthAttributes response;
}
