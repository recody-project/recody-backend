package com.recody.recodybackend.drama.data.drama;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.recody.recodybackend.common.contents.GenreIds;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;

import static com.recody.recodybackend.drama.data.drama.QDramaEntity.dramaEntity;

@Slf4j
@RequiredArgsConstructor
class DramaQueryRepositoryImpl implements DramaQueryRepository {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    @Override
    public List<DramaEntity> findByTitleLike(String keyword, Locale locale, Pageable pageable) {
        log.debug( "keyword: {}, locale: {}, pageable: {}", keyword, locale, pageable );
        JPAQuery<DramaEntity> query;
        query = createQueryFindByKeywordAndLocale( keyword, locale );
        applyPageable( query, pageable );
        return query.fetch();
    }
    
    @Override
    public Page<DramaEntity> findPagedByTitleLike(String keyword, Locale locale, Pageable pageable) {
        JPAQuery<DramaEntity> wholeQuery = createQueryFindByKeywordAndLocale( keyword, locale );
        JPAQuery<DramaEntity> pagedQuery = applyPageable( wholeQuery, pageable );
        return new PageImpl<>( pagedQuery.fetch(), pageable, wholeQuery.fetch().size() );
    }
    
    @Override
    public List<DramaEntity> findByTitleLikeFilterByGenres(String keyword, Locale locale, Pageable pageable,
                                                           GenreIds genreIds) {
        if ( genreIds.isEmpty() ) {
            return findByTitleLike( keyword, locale, pageable );
        }
        
        JPAQuery<DramaEntity> query;
        query = createQueryFindByKeywordAndLocale( keyword, locale );
        applyFilterForQuery( query, genreIds );
        applyPageable( query, pageable );
        
        return query.fetch();
    }
    
    @Override
    public Page<DramaEntity> findPagedByTitleLikeFilterByGenres(String keyword, Locale locale,
                                                                Pageable pageable, GenreIds genreIds) {
        if ( genreIds.isEmpty() ) {
            return findPagedByTitleLike( keyword, locale, pageable );
        }
        
        JPAQuery<DramaEntity> query;
        query = createQueryFindByKeywordAndLocale( keyword, locale );
        JPAQuery<DramaEntity> filteredQuery = applyFilterForQuery( query, genreIds );
        JPAQuery<DramaEntity> pagedQuery = applyPageable( query, pageable );
        return new PageImpl<>( pagedQuery.fetch(), pageable, filteredQuery.fetch().size() );
    }
    
    private static JPAQuery<DramaEntity> applyFilterForQuery(JPAQuery<DramaEntity> query, GenreIds genreIds) {
        return query.innerJoin( dramaEntity.genres )
                    .where( dramaEntity.genres.any().genreCode.id
                                    .in( genreIds.getValues() ) )
                    .distinct();
    }
    
    private static JPAQuery<DramaEntity> applyPageable(JPAQuery<DramaEntity> query, Pageable pageable) {
        if ( !pageable.isUnpaged() ) {
            return query.limit( pageable.getPageSize() )
                        .offset( pageable.getOffset() );
        }
        else {
            return query;
        }
    }
    
    private JPAQuery<DramaEntity> createQueryFindByKeywordAndLocale(String keyword, Locale locale) {
        JPAQuery<DramaEntity> query;
        if ( locale.getLanguage().equals( Locale.KOREAN.getLanguage() ) ) {
            query = createQueryWhereKoreanTitleLike( keyword );
        }
        else {
            query = createQueryWhereEnglishTitleLike( keyword );
        }
        return query;
    }
    
    private JPAQuery<DramaEntity> createQueryWhereEnglishTitleLike(String keyword) {
        return jpaQueryFactory.selectFrom( dramaEntity )
                              .innerJoin( dramaEntity.title ).fetchJoin()
                              .where( dramaEntity.title.englishTitle.contains( keyword ) );
    }
    
    private JPAQuery<DramaEntity> createQueryWhereKoreanTitleLike(String keyword) {
        return jpaQueryFactory.selectFrom( dramaEntity )
                              .innerJoin( dramaEntity.title ).fetchJoin()
                              .where( dramaEntity.title.koreanTitle.contains( keyword ) );
    }
}
