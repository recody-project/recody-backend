package com.recody.recodybackend.users.features.login;

public interface SocialLoginService {
    ProcessLoginResponse handleNaverLogin(ProcessLogin command);
    
    /*
    * - 구글 리소스 서버에서 유저 정보를 받아온다.
    * - access token 이 만료된 경우, 갱신 한다.
    * - 우리 서버에 존재하는 유저 정보인지 확인한다.
    * - 존재하지 않으면 회원가입 시킨다.
    * - 액세스 토큰과 리프레시 토큰을 만들어 반환한다. */
    ProcessLoginResponse handleGoogleLogin(ProcessLogin command);
    
    
    
    
    
    
    ProcessLoginResponse handleKakaoLogin(ProcessLogin command);
}
