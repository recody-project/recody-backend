package com.recody.recodybackend.drama.data.search;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DramaSearchingKeywordCountRepository extends JpaRepository<DramaSearchingKeywordCountEntity, Long> {
    
    Optional<DramaSearchingKeywordCountEntity> findFirstByText(String text);
    
}
