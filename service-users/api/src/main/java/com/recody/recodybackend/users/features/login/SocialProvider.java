package com.recody.recodybackend.users.features.login;

public enum SocialProvider {
    GOOGLE("GOOGLE"), NAVER("NAVER"), KAKAO("KAKAO"), APPLE("APPLE"),NONE("NONE")
    
    
    
    
    ;
    
    private final String value;
    
    SocialProvider(String value) { this.value = value; }
    
    public String getValue() { return value; }
}
