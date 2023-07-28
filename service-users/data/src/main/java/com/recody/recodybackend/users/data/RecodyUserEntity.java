package com.recody.recodybackend.users.data;

import com.recody.recodybackend.users.Role;
import com.recody.recodybackend.users.SocialProvider;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

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
public class RecodyUserEntity {
    @Id @GeneratedValue
    private Long userId;
    
    private String username;
    @Column(name = "password", nullable = false)
    @Setter
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
    
    @Setter
    @Column(name = "apple_user_identifier", nullable = true)
    private String appleUserIdentifier;
    
    @NonNull
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    
    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( !(o instanceof RecodyUserEntity) ) return false;
        RecodyUserEntity that = (RecodyUserEntity) o;
        return Objects.equals( getUserId(), that.getUserId() );
    }
    
    @Override
    public int hashCode() {
        return Objects.hash( getUserId() );
    }
}
