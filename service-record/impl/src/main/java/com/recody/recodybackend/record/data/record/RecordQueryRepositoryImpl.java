package com.recody.recodybackend.record.data.record;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.recody.recodybackend.record.data.category.EmbeddableCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.recody.recodybackend.record.data.content.QRecordContentEntity.recordContentEntity;
import static com.recody.recodybackend.record.data.record.QRecordEntity.recordEntity;

@Repository
@RequiredArgsConstructor
class RecordQueryRepositoryImpl implements RecordQueryRepository{
    
    private final JPAQueryFactory jpaQueryFactory;
    
    @Override
    public List<RecordEntity> findAllFetchJoinContentOnCategory(EmbeddableCategory category) {
        return jpaQueryFactory.selectFrom(recordEntity)
                              .leftJoin(recordContentEntity)
                              .on(recordContentEntity.category.eq(category)).fetchJoin()
                              .where(recordEntity.content.category.eq(category))
                              .fetch();
    }
    
    @Override
    public List<RecordEntity> findAllFetchJoinContentWhereCategoryAndUserId(EmbeddableCategory category,
                                                                            Long userid) {
        return jpaQueryFactory.selectFrom(recordEntity)
                              .leftJoin(recordContentEntity)
                              .on(recordContentEntity.category.eq(category))
                              .where(recordEntity.content.category.eq(category),
                                     recordEntity.userId.eq(userid))
                              .fetch();
    }
}
