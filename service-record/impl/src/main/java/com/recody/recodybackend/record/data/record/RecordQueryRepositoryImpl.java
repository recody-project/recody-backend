package com.recody.recodybackend.record.data.record;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.recody.recodybackend.record.data.category.EmbeddableCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.recody.recodybackend.record.data.record.QRecordEntity.recordEntity;

@Repository
@RequiredArgsConstructor
class RecordQueryRepositoryImpl implements RecordQueryRepository{
    
    private final JPAQueryFactory jpaQueryFactory;
    
    @Override
    public List<RecordEntity> findAllFetchJoinContentOnCategory(EmbeddableCategory category) {
        return jpaQueryFactory.selectFrom(recordEntity)
                              .leftJoin(recordEntity.content)
                              .fetchJoin()
                              .where(recordEntity.content.category.eq(category))
                              .fetch();
    }
    
    @Override
    public List<RecordEntity> findAllFetchJoinContentWhereCategoryAndUserId(EmbeddableCategory category,
                                                                            Long userid) {
        return jpaQueryFactory.selectFrom(recordEntity)
                              .leftJoin(recordEntity.content)
                              .where(recordEntity.content.category.eq(category),
                                     recordEntity.userId.eq(userid)).fetchJoin()
                              .fetch();
    }
    
    @Override
    public Optional<List<RecordEntity>> findAllFetchJoinContentWhereCategoryAndUserIdLimit(EmbeddableCategory category,
                                                                                          Long userId, Pageable pageable) {
        if (pageable.isUnpaged()){
            return Optional.of(doFetch(category, userId));
        }
        return Optional.of(deFetch(category, userId, pageable));
    }
    
    private List<RecordEntity> deFetch(EmbeddableCategory category, Long userId, Pageable pageable) {
        return jpaQueryFactory
                       .selectFrom(recordEntity)
                       .leftJoin(recordEntity.content)
                       .where(recordEntity.content.category.eq(category), recordEntity.userId.eq(userId))
                       .limit(pageable.getPageSize())
                       .offset(pageable.getOffset())
                       .orderBy(recordEntity.createdAt.desc())
                       .fetch();
    }
    
    private List<RecordEntity> doFetch(EmbeddableCategory category, Long userId) {
        return jpaQueryFactory
                       .selectFrom(recordEntity)
                       .leftJoin(recordEntity.content)
                       .where(recordEntity.content.category.eq(category), recordEntity.userId.eq(userId))
                       .orderBy(recordEntity.createdAt.desc())
                       .fetch();
    }
}
