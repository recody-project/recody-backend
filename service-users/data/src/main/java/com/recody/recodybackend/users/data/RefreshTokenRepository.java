package com.recody.recodybackend.users.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

//    RefreshTokenEntity getReferenceByRefreshTokenValue(String refreshTokenValue);
    Optional<RefreshTokenEntity> findByRefreshTokenValue(String refreshTokenValue);
}
