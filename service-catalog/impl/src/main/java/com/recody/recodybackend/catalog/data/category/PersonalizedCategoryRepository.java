package com.recody.recodybackend.catalog.data.category;

import com.recody.recodybackend.catalog.data.LookupId;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalizedCategoryRepository extends JpaRepository<PersonalizedCategoryEntity, LookupId> {
    
    Optional<PersonalizedCategoryEntity> findByUserIdAndContent(Long userId, CatalogContentEntity content);
    
}
