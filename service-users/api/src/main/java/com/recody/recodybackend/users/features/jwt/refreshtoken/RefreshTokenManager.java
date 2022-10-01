package com.recody.recodybackend.users.features.jwt.refreshtoken;

import com.recody.recodybackend.users.features.login.membership.RecodySignInSession;

public interface RefreshTokenManager {
    
    /**
     * 로그인 세션을 레코디 서비스에서 관리하도록 처리한다.
     * 리프레시 토큰을 저장한다.
     * 저장하기 위해서는 리프레시 토큰 값 뿐만 아니라 User-Agent 값, 유저 정보 (RecodyUserInfo) 가 필요하다.
     */
    void integrate(RecodySignInSession signInInfo, String userAgent);
    
    String reissue(String refreshToken, String userAgent);
    
    
}
