package com.recody.recodybackend.users.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VerificationCodeRepository extends JpaRepository<VerificationCodeEntity, UUID> {
    
    
    Optional<VerificationCodeEntity> findByUser(RecodyUserEntity user);
    
    Optional<VerificationCodeEntity> findFirstByUserOrderByCreatedAtDesc(RecodyUserEntity user);
}
