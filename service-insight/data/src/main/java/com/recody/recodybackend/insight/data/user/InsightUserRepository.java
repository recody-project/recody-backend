package com.recody.recodybackend.insight.data.user;

import com.recody.recodybackend.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsightUserRepository extends JpaRepository<InsightUserEntity, Long> {
    
    List<InsightUserEntity> findAllByRole(Role role);
    
}
