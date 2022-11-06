package com.recody.recodybackend.catalog.data.wish;

import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WishRepository extends JpaRepository<WishEntity, UUID> {
    boolean existsByCatalogContentAndUserId(CatalogContentEntity catalogContent, Long userId);
    
    Optional<WishEntity> findByCatalogContentAndUserId(CatalogContentEntity catalogContent, Long userId);
    
    Optional<List<WishEntity>> findAllByUserId(Long userId);
    
    
}
