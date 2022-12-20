package com.recody.recodybackend.drama.data.drama;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
        if ( locale.getLanguage().equals( Locale.KOREAN.getLanguage() ) ) {
            query = createQueryWhereKoreanTitleLike( keyword );
        }
        else {
            query = createQueryWhereEnglishTitleLike( keyword );
        }
        if ( !pageable.isUnpaged() ) {
            query.limit( pageable.getPageSize() )
                 .offset( pageable.getOffset() );
        }
        return query.fetch();
    }
    
    private JPAQuery<DramaEntity> createQueryWhereKoreanTitleLike(String keyword) {
        return jpaQueryFactory.selectFrom( dramaEntity )
                              .innerJoin( dramaEntity.title )
                              .where( dramaEntity.title.koreanTitle.contains( keyword ) );
    }
    
    private JPAQuery<DramaEntity> createQueryWhereEnglishTitleLike(String keyword) {
        return jpaQueryFactory.selectFrom( dramaEntity )
                              .innerJoin( dramaEntity.title )
                              .where( dramaEntity.title.englishTitle.contains( keyword ) );
    }
}
