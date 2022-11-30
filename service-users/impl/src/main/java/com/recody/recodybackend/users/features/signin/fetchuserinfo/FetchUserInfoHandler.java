package com.recody.recodybackend.users.features.signin.fetchuserinfo;

import com.recody.recodybackend.users.OAuthUserInfo;

/*
* 각 소셜 로그인 서비스에서 유저의 정보를 가져온다.
* 가져온 정보는 로그인 처리에 쓰인다. */
public interface FetchUserInfoHandler {
    
    OAuthUserInfo handle(FetchUserInfo command);
    
}
