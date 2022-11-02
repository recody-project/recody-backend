package com.recody.recodybackend.users.sociallogin;

import com.recody.recodybackend.users.SocialProvider;

public class ResourceAccessToken {
    
    private final String value;
    private final SocialProvider socialProvider;
    
    
    private ResourceAccessToken(String accessToken, SocialProvider socialProvider) {
        this.value = accessToken;
        this.socialProvider = socialProvider;
    }
    
    
    public static ResourceAccessToken googleOf(String accessToken) {
        return new ResourceAccessToken(accessToken, SocialProvider.GOOGLE);
    }
    
    public static ResourceAccessToken kakaoOf(String accessToken) {
        return new ResourceAccessToken(accessToken, SocialProvider.KAKAO);
    }
    
    public static ResourceAccessToken naverOf(String accessToken) {
        return new ResourceAccessToken(accessToken, SocialProvider.NAVER);
    }
    
    public static ResourceAccessToken appleOf(String accessToken) {
        return new ResourceAccessToken(accessToken, SocialProvider.APPLE);
    }
    
    
    public String getValue() {
        return value;
    }
    
    
    public SocialProvider getSocialProvider() {
        return socialProvider;
    }
    
    
    @Override
    public String toString() {
        return "{\"ResourceAccessToken\":{" + "\"value\":" + ((value != null) ? ("\"" + value + "\"") : null) + ", \"socialProvider\":" + ((socialProvider != null) ? ("\"" + socialProvider + "\"") : null) + "}}";
    }
}
