package com.recody.recodybackend.catalog.data.record;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
        return createQueryFindRecordsWhereCategoryAndUserId( category, userid ).fetchJoin()
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
        return Optional.of( doFetch( category, userId, pageable ) );
    }
    
    @Override
    public Page<RecordEntity> findAllFetchJoinContentWhereCategoryAndUserIdLimitPage(CategoryEntity category, Long userId,
                                                                                     Pageable pageable) {
        if ( pageable.isUnpaged() ) {
            return new PageImpl<>( doFetch( category, userId ) );
        }
        JPAQuery<RecordEntity> queryAll = createQueryFindRecordsWhereCategoryAndUserId( category, userId );
        JPAQuery<RecordEntity> pagedQuery = QueryDslUtils.applyPageable( pageable, queryAll );
        List<RecordEntity> content = pagedQuery
                                           .orderBy( recordEntity.createdAt.desc() )
                                           .fetch();
        
        return new PageImpl<>( content, pageable, queryAll.fetch().size() );
    }
    
    @Override
    public Optional<List<RecordEntity>> findAllByContentIdAndUserId(Long userId, String contentId, Pageable pageable) {
        return Optional.of( createQueryFindRecordsWhereUserIdAndContentId( userId, contentId )
                                           .limit( pageable.getPageSize() )
                                           .offset( pageable.getOffset() )
                                           .orderBy( QueryDslUtils.getOrderSpecifiers( pageable.getSort(), recordEntity ) )
                                           .fetch()
                          );
    }
    
    private JPAQuery<RecordEntity> createQueryFindRecordsWhereUserIdAndContentId(Long userId, String contentId) {
        return jpaQueryFactory.selectFrom( recordEntity )
                              .leftJoin( recordEntity.content )
                              .where( recordEntity.user.id.eq( userId ), recordEntity.content.contentId.eq( contentId ) );
    }
    
    @Override
    public Page<RecordEntity> findAllByContentIdAndUserIdPage(Long userId, String contentId, Pageable pageable) {
        JPAQuery<RecordEntity> totalQuery = createQueryFindRecordsWhereUserIdAndContentId( userId, contentId );
        int totalSize = totalQuery.fetch().size();
        JPAQuery<RecordEntity> recordEntityJPAQuery = QueryDslUtils.applyPageable( pageable, totalQuery );
        return new PageImpl<>( recordEntityJPAQuery.fetch(), pageable, totalSize );
    }
    
    private List<RecordEntity> doFetch(CategoryEntity category, Long userId, Pageable pageable) {
        JPAQuery<RecordEntity> queryFindRecordsWhereCategoryAndUserId = createQueryFindRecordsWhereCategoryAndUserId( category, userId );
        JPAQuery<RecordEntity> recordEntityJPAQuery = QueryDslUtils.applyPageable(
                pageable, queryFindRecordsWhereCategoryAndUserId );
        return recordEntityJPAQuery
                       .orderBy( recordEntity.createdAt.desc() )
                       .fetch();
    }
    
    private JPAQuery<RecordEntity> createQueryFindRecordsWhereCategoryAndUserId(CategoryEntity category, Long userId) {
        return jpaQueryFactory
                       .selectFrom( recordEntity )
                       .leftJoin( recordEntity.content )
                       .where( recordEntity.content.category.eq( category ), recordEntity.user.id.eq( userId ) );
    }
    
    private List<RecordEntity> doFetch(CategoryEntity category, Long userId) {
        return createQueryFindRecordsWhereCategoryAndUserId( category, userId )
                       .orderBy( recordEntity.createdAt.desc() )
                       .fetch();
    }
}
