package com.recody.recodybackend.drama.data.search;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DramaSearchKeywordRepository extends JpaRepository<DramaSearchKeywordEntity, Long> {
    
    Optional<DramaSearchKeywordEntity> findFirstByText(String text);
    
}
