package com.recody.recodybackend.catalog.data.user;

import com.recody.recodybackend.users.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "catalog_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Getter
public class CatalogUserEntity {
    
    @Id
    private Long id;
    
    @NonNull
    @Column( name = "email", nullable = false)
    private String email;
    
    @NonNull
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    
    private String nickname;
    
    @Column(name = "picture_url")
    private String pictureUrl;
    
    @Override
    public String toString() {
        return "{\"CatalogUserEntity\":{"
               + "\"userId\":" + id
               + ", \"email\":" + ((email != null) ? ("\"" + email + "\"") : null)
               + ", \"role\":" + ((role != null) ? ("\"" + role + "\"") : null)
               + "}}";
    }
}
