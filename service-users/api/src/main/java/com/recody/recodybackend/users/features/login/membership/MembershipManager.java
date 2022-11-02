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
    
    
    /**
     * 어드민 유저를 회원가입 시킵니다.
     * 현재 기본 어드민 유저 외에는 가입시키지 않습니다.
     */
    RecodyUserInfo signUpAdmin(AdminUserInfo userInfo);
    
    
    RecodySignInSession createSessionInfo(RecodyUserInfo recodyUserInfo, String userAgent);
    
}
