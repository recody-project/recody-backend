package com.recody.recodybackend.users.features.signin.googlelogin;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefreshGoogleAccessTokenResponse{
    @JsonAlias("access_token")
    private String accessToken;
    
    @JsonAlias("expires_in")
    private Integer expiresIn;
    
    private String scope;
    
    @JsonAlias("token_type")
    private String tokenType;
    
    private boolean succeeded;
}
