package com.recody.recodybackend.catalog.data.user;

import com.recody.recodybackend.users.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "catalog_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class CatalogUserEntity {
    
    @Id
    private Long userId;
    
    @NonNull
    @Column( name = "email", nullable = false)
    private String email;
    
    @NonNull
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    
    @Override
    public String toString() {
        return "{\"CatalogUserEntity\":{"
               + "\"userId\":" + userId
               + ", \"email\":" + ((email != null) ? ("\"" + email + "\"") : null)
               + ", \"role\":" + ((role != null) ? ("\"" + role + "\"") : null)
               + "}}";
    }
}