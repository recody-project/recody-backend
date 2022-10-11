package com.recody.recodybackend.record.data.record;

import com.recody.recodybackend.record.data.category.EmbeddableCategory;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface RecordQueryRepository {
    
    /**
     * @param category : 찾고자하는 작품의 카테고리
     * @return 감상평을 남긴 작품의 카테고리가 category 인 모든 감상평
     */
    List<RecordEntity> findAllFetchJoinContentOnCategory(EmbeddableCategory category);
    
    
    List<RecordEntity> findAllFetchJoinContentWhereCategoryAndUserId(EmbeddableCategory category, Long userid);
    
    
    Optional<List<RecordEntity>> findAllFetchJoinContentWhereCategoryAndUserIdLimit(EmbeddableCategory category, Long userid, Pageable pageable);
    Optional<List<RecordEntity>> findAllByContentIdAndUserId(Long userId, String contentId, Pageable pageable);
}
