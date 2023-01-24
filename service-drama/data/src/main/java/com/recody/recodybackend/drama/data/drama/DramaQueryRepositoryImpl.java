package com.recody.recodybackend.drama.data.drama;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.recody.recodybackend.common.contents.GenreIds;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    
    private static void applyFilterForQuery(JPAQuery<DramaEntity> query, GenreIds genreIds) {
        query.innerJoin( dramaEntity.genres )
             .where( dramaEntity.genres.any().genreCode.id
                             .in( genreIds.getValues() ) )
             .distinct();
    }
    
    private static void applyPageable(JPAQuery<DramaEntity> query, Pageable pageable) {
        if ( !pageable.isUnpaged() ) {
            query.limit( pageable.getPageSize() )
                 .offset( pageable.getOffset() );
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
