package com.recody.recodybackend.users.data;

import com.recody.recodybackend.users.features.login.SocialProvider;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecodyUser {
    @Id @GeneratedValue
    private Long userId;
    
    private String username;
    private String pictureUri;
    private String email;
    private String nickname;
    private String password;
    
    @Enumerated(value = EnumType.STRING)
    private SocialProvider socialType;
    
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
