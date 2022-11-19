package com.recody.recodybackend.catalog.data.content;

import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogContentRepository extends JpaRepository<CatalogContentEntity, String> {
    
    Optional<CatalogContentEntity> findByContentId(String contentId);
    
    boolean existsByContentId(String contentId);
    
    
    Optional<CatalogContentEntity> findByContentIdAndCategory_Id(String contentId, String categoryId);
    
    Optional<CatalogContentEntity> findByContentIdAndCategory(String contentId, CategoryEntity categoryEntity);
    
}
