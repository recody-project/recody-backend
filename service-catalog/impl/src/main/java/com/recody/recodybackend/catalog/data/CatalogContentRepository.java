package com.recody.recodybackend.catalog.data;

import com.recody.recodybackend.common.contents.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogContentRepository extends JpaRepository<CatalogContentEntity, String> {
    
    Optional<CatalogContentEntity> findByContentId(String contentId);
    
    Optional<CatalogContentEntity> findByContentIdAndCategory(String contentId, Category category);
    
}
