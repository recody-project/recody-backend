package com.recody.recodybackend.users;

/**
 * 소셜 로그인 서비스에서 받아온 유저 정보이다.
 * 여러 소셜 로그인 서비스 마다 구현한다.
 * 레코디에서 사용할 여러 유저 정보를 가지고 있다.
 * 이 정보만으로는 아직 회원가입되었는지는 알 수 없다. */
public interface OAuthUserInfo {
    
    String getName();
    String getEmail();
    SocialProvider getSocialProvider();
    String getProfileImageUrl();
    
    
}
