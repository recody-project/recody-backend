package com.recody.recodybackend.users.data;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "REFRESH_TOKEN")
public class RefreshTokenEntity {
    @Id
    @GeneratedValue
    private Long refreshTokenId;
    
    @Column(nullable = false, name = "refresh_token")
    private String refreshTokenValue;
    
    @Column(nullable = false)
    private String userAgent;
    
    private String subject;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RefreshTokenEntity that = (RefreshTokenEntity) o;
        return refreshTokenId != null && Objects.equals(refreshTokenId, that.refreshTokenId);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return "{\"RefreshTokenEntity\":{" + "\"refreshTokenId\":" + refreshTokenId + ", \"refreshTokenValue\":" + ((refreshTokenValue != null) ? ("\"" + refreshTokenValue + "\"") : null) + ", \"userAgent\":" + ((userAgent != null) ? ("\"" + userAgent + "\"") : null) + ", \"subject\":" + ((subject != null) ? ("\"" + subject + "\"") : null) + "}}";
    }
}
