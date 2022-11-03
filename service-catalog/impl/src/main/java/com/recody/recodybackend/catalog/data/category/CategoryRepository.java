package com.recody.recodybackend.catalog.data.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, String>, CategoryQueryRepository{
    
    Optional<CategoryEntity> findByNameAndUserId(String name, Long userId);
    
    Optional<CategoryEntity> findByIdAndUserId(String id, Long userId);
    
    List<CategoryEntity> findAllByUserId(Long userId);
    
    int countByUserIdAndBasicIsFalse(Long userId);
    
}
