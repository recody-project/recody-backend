package com.recody.recodybackend.catalog.data.record;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.recody.recodybackend.catalog.data.record.QRecordEntity.recordEntity;


@Repository
@RequiredArgsConstructor
class RecordQueryRepositoryImpl implements RecordQueryRepository {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    @Override
    public List<RecordEntity> findAllFetchJoinContentOnCategory(CategoryEntity category) {
        return jpaQueryFactory.selectFrom( recordEntity )
                              .leftJoin( recordEntity.content )
                              .fetchJoin()
                              .where( recordEntity.content.category.eq( category ) )
                              .fetch();
    }
    
    @Override
    public List<RecordEntity> findAllFetchJoinContentWhereCategoryAndUserId(CategoryEntity category,
                                                                            Long userid) {
        return jpaQueryFactory.selectFrom( recordEntity )
                              .leftJoin( recordEntity.content )
                              .where(
                                      recordEntity.content.category.eq( category ),
                                      recordEntity.user.id.eq( userid ) ).fetchJoin()
                              .fetch();
    }
    
    @Override
    public Integer countByUserIdAndAppreciationDateAfter(Long userId, LocalDate date) {
        return Math.toIntExact(
                jpaQueryFactory.select( recordEntity.count() )
                               .from( recordEntity )
                               .where( recordEntity.user.id.eq( userId ) )
                               .where( recordEntity.appreciationDate.after( date ) )
                               .fetchFirst() )
                ;
    }
    
    @Override
    public Optional<List<RecordEntity>> findAllFetchJoinContentWhereCategoryAndUserIdLimit(CategoryEntity category,
                                                                                           Long userId, Pageable pageable) {
        if ( pageable.isUnpaged() ) {
            return Optional.of( doFetch( category, userId ) );
        }
        return Optional.of( deFetch( category, userId, pageable ) );
    }
    
    @Override
    public Optional<List<RecordEntity>> findAllByContentIdAndUserId(Long userId, String contentId, Pageable pageable) {
        return Optional.of( jpaQueryFactory.selectFrom( recordEntity )
                                           .leftJoin( recordEntity.content )
                                           .where( recordEntity.user.id.eq( userId ), recordEntity.content.contentId.eq( contentId ) )
                                           .limit( pageable.getPageSize() )
                                           .offset( pageable.getOffset() )
                                           .orderBy( QueryDslUtils.getOrderSpecifiers( pageable.getSort(), recordEntity ) )
                                           .fetch()
                          );
    }
    
    private List<RecordEntity> deFetch(CategoryEntity category, Long userId, Pageable pageable) {
        return jpaQueryFactory
                       .selectFrom( recordEntity )
                       .leftJoin( recordEntity.content )
                       .where( recordEntity.content.category.eq( category ), recordEntity.user.id.eq( userId ) )
                       .limit( pageable.getPageSize() )
                       .offset( pageable.getOffset() )
                       .orderBy( recordEntity.createdAt.desc() )
                       .fetch();
    }
    
    private List<RecordEntity> doFetch(CategoryEntity category, Long userId) {
        return jpaQueryFactory
                       .selectFrom( recordEntity )
                       .leftJoin( recordEntity.content )
                       .where( recordEntity.content.category.eq( category ), recordEntity.user.id.eq( userId ) )
                       .orderBy( recordEntity.createdAt.desc() )
                       .fetch();
    }
}
