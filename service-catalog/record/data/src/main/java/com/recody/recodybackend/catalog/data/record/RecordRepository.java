package com.recody.recodybackend.catalog.data.record;

import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<RecordEntity, String>, RecordQueryRepository {
    
    Optional<RecordEntity> findByRecordId(String recordId);
    
    Optional<RecordEntity> findByRecordIdAndUser(String recordId, CatalogUserEntity user);
    
    Optional<List<RecordEntity>> findAllByUserId(Long userId);
    
    /**
     * content Id 와 user Id 로 모든 감상평들을 가져온다.*/
    Optional<List<RecordEntity>> findAllByUserIdAndContent(Long userId, CatalogContentEntity content);
    boolean existsByUserIdAndContent(Long userId, CatalogContentEntity content);
    
    boolean existsByUserAndContentAndNth(CatalogUserEntity user, CatalogContentEntity content, Integer nth);
    
    Integer countByUserId(Long userId);
    

    
    
    /**
     * 생성일 기준 최근부터 가져온다.
     * pageable: n개씩 가져올지, n개씩 몇번째 페이지 부터 가져올지 정한다.
     */
    Optional<List<RecordEntity>> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    Optional<List<RecordEntity>> findByUserIdAndCompletedOrderByCreatedAtDesc(Long userId, boolean isCompleted, Pageable pageable);
    
    /**
     * 최근 수정된 레코드들 중에서
     * userId == :userId 이고, completed == false 인 가장 첫번째 레코드를 가져온다.
     * */
    Optional<RecordEntity> findFirstByUserIdAndCompletedIsFalseOrderByLastModifiedAtDesc(Long userId);
    
    Optional<List<RecordEntity>> findAllByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start,
                                                                    LocalDateTime end);
    Optional<List<RecordEntity>> findTop10ByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start,
                                                                      LocalDateTime end);
}
