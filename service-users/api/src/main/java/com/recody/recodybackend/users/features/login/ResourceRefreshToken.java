package com.recody.recodybackend.users.features.login;

public class ResourceRefreshToken {
    
    private final String value;
    private final SocialProvider socialProvider;
    
    
    private ResourceRefreshToken(String refreshToken, SocialProvider socialProvider) {
        this.value = refreshToken;
        this.socialProvider = socialProvider;
    }
    
    
    public static ResourceRefreshToken googleOf(String refreshToken) {
        return new ResourceRefreshToken(refreshToken, SocialProvider.GOOGLE);
    }
    
    public static ResourceRefreshToken kakaoOf(String refreshToken) {
        return new ResourceRefreshToken(refreshToken, SocialProvider.KAKAO);
    }
    
    public static ResourceRefreshToken naverOf(String refreshToken) {
        return new ResourceRefreshToken(refreshToken, SocialProvider.NAVER);
    }
    
    public static ResourceRefreshToken appleOf(String refreshToken) {
        return new ResourceRefreshToken(refreshToken, SocialProvider.APPLE);
    }
    
    
    public String getValue() {
        return value;
    }
    
    
    public SocialProvider getSocialProvider() {
        return socialProvider;
    }
    
    
    @Override
    public String toString() {
        return "{\"ResourceRefreshToken\":{" + "\"value\":" + ((value != null) ? ("\"" + value + "\"") : null) + ", \"socialProvider\":" + ((socialProvider != null) ? ("\"" + socialProvider + "\"") : null) + "}}";
    }
}
