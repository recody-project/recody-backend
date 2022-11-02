package com.recody.recodybackend.users.data;

import com.recody.recodybackend.users.Role;
import com.recody.recodybackend.users.SocialProvider;
import lombok.*;

import javax.persistence.*;

/**
 * UniqueConstraint: email 과 social_type 컬럼의 조합이 유일해야 한다.
 * */

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recody_user",
       uniqueConstraints = {
        @UniqueConstraint(name = "recody_user_social_identity", columnNames = {"email", "social_type"})
})
public class RecodyUser {
    @Id @GeneratedValue
    private Long userId;
    
    private String username;
    private String password;
    
    private String pictureUrl;
    private String name;
    private String nickname;
    
    @NonNull
    @Column(name = "email", nullable = false)
    private String email;
    
    
    @NonNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "social_type", nullable = false)
    private SocialProvider socialType;
    
    @NonNull
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
