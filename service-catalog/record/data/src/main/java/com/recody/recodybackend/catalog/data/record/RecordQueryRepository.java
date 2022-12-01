package com.recody.recodybackend.catalog.data.record;

import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RecordQueryRepository {
    
    /**
     * @param category : 찾고자하는 작품의 카테고리
     * @return 감상평을 남긴 작품의 카테고리가 category 인 모든 감상평
     */
    List<RecordEntity> findAllFetchJoinContentOnCategory(CategoryEntity category);
    
    
    List<RecordEntity> findAllFetchJoinContentWhereCategoryAndUserId(CategoryEntity category, Long userid);
    
    /**
     * 특정 유저가 date 이후로 작성한 감상평의 개수를 반환한다. <br><br>
     *
     * <b> 샘플 쿼리 </b>
     * <pre>
     *     {@code
     *     select
     *         count(recordenti0_.record_id) as col_0_0_
     *     from
     *         record recordenti0_
     *     where
     *         (
     *             recordenti0_.deleted_at IS null
     *         )
     *         and recordenti0_.user_id=?
     *         and recordenti0_.appreciation_date>? limit ?}
     * </pre>
     */
    Integer countByUserIdAndAppreciationDateAfter(Long userId, LocalDate date);
    
    
    
    Optional<List<RecordEntity>> findAllFetchJoinContentWhereCategoryAndUserIdLimit(CategoryEntity category, Long userid, Pageable pageable);
    Optional<List<RecordEntity>> findAllByContentIdAndUserId(Long userId, String contentId, Pageable pageable);
    
    Page<RecordEntity> findAllByContentIdAndUserIdPage(Long userId, String contentId, Pageable pageable);
}
