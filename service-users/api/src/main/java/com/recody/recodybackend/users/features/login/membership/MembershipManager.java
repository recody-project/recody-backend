package com.recody.recodybackend.users.features.login.membership;

import com.recody.recodybackend.users.OAuthUserInfo;
import com.recody.recodybackend.users.RecodySignInSession;
import com.recody.recodybackend.users.RecodyUserInfo;

public interface MembershipManager {
    
    /**
     * 존재하는 회원인지 확인한다.
     */
    boolean exists(String email);
    
    /**
     * 소셜 로그인 유저 정보를 레코디 유저 정보로 반환한다.
     * 존재하지 않는 회원이라면 회원가입시킨다.
     * 이 메서드는 항상 Member Role 을 부여한다.
     * @param userInfo: 소셜 로그인 유저 정보(email, nickname, socialProvider, username, profileUrl)
     * @return : 레코디 유저 정보(userId, email)
     * */
    RecodyUserInfo signUp(OAuthUserInfo userInfo);
    
    
    RecodyUserInfo signUpAdmin(AdminUserInfo userInfo);
    
    /**
     * 레코디 유저를 로그인 처리하고 토큰들을 반환한다.
     *
     * @param recodyUserInfo: 레코디 유저정보입니다.
     * @return : accessToken, refreshToken 을 포함하는 로그인 응답을 반환한다.
     */
    RecodySignInSession signIn(RecodyUserInfo recodyUserInfo);
    
    /**
     * 소셜로그인 정보를 이용하여 로그인 처리하고 토큰을 반환한다.
     * 유저가 레코디 서비스에 회원가입되어 있다고 가정하고 처리한다.
     * @param userInfo: 소셜 로그인 유저 정보(email, nickname, socialProvider, username, profileUrl)
     * @return : accessToken, refreshToken 을 포함하는 로그인 응답을 반환한다.
     * */
    RecodySignInSession signIn(OAuthUserInfo userInfo);
    
}
