package com.recody.recodybackend.users.data;

import com.recody.recodybackend.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface RecodyUserRepository extends JpaRepository<RecodyUserEntity, Long> {
    
    Optional<RecodyUserEntity> findByAppleUserIdentifier(@Nullable String appleUserIdentifier);
    Optional<RecodyUserEntity> findFirstBy();
    Optional<RecodyUserEntity> findByEmail(String email);
    RecodyUserEntity getByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<RecodyUserEntity> findAllByRole(Role role);
    
    Optional<RecodyUserEntity> findByUsername(String username);
    RecodyUserEntity getByUsername(String username);
}
