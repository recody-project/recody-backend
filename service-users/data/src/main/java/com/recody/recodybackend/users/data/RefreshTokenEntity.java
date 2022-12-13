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
    
    @NonNull
    @Column(nullable = false, name = "refresh_token")
    private String refreshTokenValue;
    
    /**
     * 토큰을 재발급할 때 user-agent 헤더가 같은지 확인한다. */
    @Column(nullable = false)
    private String userAgent;
    
    /**
     * 토큰을 만들 때 사용된 subject 를 의미한다.
     * JwtManager 구현에 따라 email, username 등일 수 있다.*/
    @NonNull
    private String subject;
    
    /**
     * 소셜 로그인 Provider 가 제공한 리소스 접근 refresh token 을 의미한다. */
    @Column(name = "resource_refresh_token")
    private String resourceRefreshToken;
    
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
