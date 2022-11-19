package com.recody.recodybackend.catalog.data.user;

import com.recody.recodybackend.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatalogUserRepository extends JpaRepository<CatalogUserEntity, Long> {
    
    List<CatalogUserEntity> findAllByRole(Role role);

}
