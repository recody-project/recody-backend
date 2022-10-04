package com.recody.recodybackend.catalog.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;

public interface WishRepository extends JpaRepository<WishEntity, UUID> {
    boolean existsByCatalogContentAndUserId(CatalogContentEntity catalogContent, Long userId);
    @Lock(LockModeType.OPTIMISTIC)
    Optional<WishEntity> findByCatalogContentAndUserId(CatalogContentEntity catalogContent, Long userId);
}
