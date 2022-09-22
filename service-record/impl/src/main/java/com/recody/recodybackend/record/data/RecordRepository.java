package com.recody.recodybackend.record.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<RecordEntity, String> {
    
    Optional<RecordEntity> findByRecordId(String recordId);
    
    Optional<List<RecordEntity>> findAllByUserId(Long userId);
    
    /**
     * 생성일 기준 최근부터 가져온다.
     * pageable: n개씩 가져올지, n개씩 몇번째 페이지 부터 가져올지 정한다.
     */
    Optional<List<RecordEntity>> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    
    
    Optional<List<RecordEntity>> findAllByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start,
                                                                    LocalDateTime end);
    Optional<List<RecordEntity>> findTop10ByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start,
                                                                      LocalDateTime end);
}
