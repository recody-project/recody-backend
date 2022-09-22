package com.recody.recodybackend.record.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<RecordEntity, String> {
    
    Optional<RecordEntity> findByRecordId(String recordId);
    
    Optional<List<RecordEntity>> findAllByUserId(Long userId);
}
