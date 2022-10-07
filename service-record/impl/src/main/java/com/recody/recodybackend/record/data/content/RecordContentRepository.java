package com.recody.recodybackend.record.data.content;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordContentRepository extends JpaRepository<RecordContentEntity, String> {
    
    Optional<RecordContentEntity> findByContentId(String contentId);

}
