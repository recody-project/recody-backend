package com.recody.recodybackend.users.data;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@AllArgsConstructor
@Table(name = "users_verification_code")
@EntityListeners( AuditingEntityListener.class)
public class VerificationCodeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID uuid;
    
    /**
     * 하나의 유저와 매핑된다.
     * <li>유저가 삭제되면 VerificationCodeEntity 는 cascade delete 처리된다.</li>
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RecodyUserEntity user;
    
    @Column(name = "verification_code", nullable = false, updatable = false)
    private String verificationCode;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @Override
    public String toString() {
        return "{\"VerificationCodeEntity\":{"
               + "\"uuid\":" + ((uuid != null) ? ("\"" + uuid + "\"") : null)
               + ", \"user\":" + user
               + ", \"verificationCode\":" + ((verificationCode != null) ? ("\"" + verificationCode + "\"") : null)
               + "}}";
    }
}
