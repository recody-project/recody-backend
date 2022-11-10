package com.recody.recodybackend.catalog.data.rating;

import com.recody.recodybackend.catalog.data.LookupId;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<RatingEntity, LookupId> {
    
    
    Optional<RatingEntity> findByUserIdAndContent(Long userId, CatalogContentEntity content);
    Optional<RatingEntity> findByUserIdAndContent_Id(Long userId, String contentId);
}
