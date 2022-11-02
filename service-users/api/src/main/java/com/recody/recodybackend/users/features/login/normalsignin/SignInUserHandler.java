package com.recody.recodybackend.users.features.login.normalsignin;

import com.recody.recodybackend.users.RecodySignInSession;

public interface SignInUserHandler {
    
    /**
     * 레코디 유저를 로그인 처리하고 토큰들을 반환한다.
     * @param command: 로그인을 위한 정보
     * @return : accessToken, refreshToken 을 포함하는 로그인 응답을 반환한다.
     */
    RecodySignInSession handle(SignInUser command);
    
}
