package com.recody.recodybackend.catalog.data.user;

import com.recody.recodybackend.users.Role;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( !(o instanceof CatalogUserEntity) ) return false;
        CatalogUserEntity that = (CatalogUserEntity) o;
        return Objects.equals( getId(), that.getId() );
    }
    
    @Override
    public int hashCode() {
        return Objects.hash( getId() );
    }
    
    @Override
    public String toString() {
        return "{\"CatalogUserEntity\":{"
               + "\"id\":" + id
               + "}}";
    }
}
