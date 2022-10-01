package com.recody.recodybackend.users.features.login.membership;

import com.recody.recodybackend.users.data.Role;
import com.recody.recodybackend.users.features.login.SocialProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * 회원 가입을 한 레코디의 유저 정보이다.
 * 엔티티와 매핑될 수 있다.
 * 패스워드는 포함하지 않는다.
 *
 * */
@Getter
@Builder
public class RecodyUserInfo {
    
    @NonNull
    private Long userId;
    private String username;
    
    @NonNull
    private String email;
    private String name;
    private SocialProvider socialType;
    private String nickname;
    
    @NonNull
    private Role role;
    private String pictureUrl;
    
}
