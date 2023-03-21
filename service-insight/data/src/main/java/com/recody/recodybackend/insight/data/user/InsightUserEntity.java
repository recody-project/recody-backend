package com.recody.recodybackend.insight.data.user;

import com.recody.recodybackend.users.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Table( name = "insight_user")
@AllArgsConstructor
@NoArgsConstructor( access = lombok.AccessLevel.PROTECTED )
@Getter
@Builder
public class InsightUserEntity {

    @Id
    private Long id;
    
    private String nickname;
    
    @NonNull
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
