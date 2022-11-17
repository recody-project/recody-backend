package com.recody.recodybackend.movie.data.movie;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.recody.recodybackend.movie.data.movie.QMovieEntity.movieEntity;


@Repository
@RequiredArgsConstructor
@Slf4j
class MovieQueryRepositoryImpl implements MovieQueryRepository {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    
    /**
     * 요청 언어가 한국어 -> 영어, 한국어 모두 검색한다.
     * 요청 언어가 영어 -> 영어만 검색한다.
     *
     * @param title  검색어
     * @param locale 요청 언어
     * @return 영화 정보를 담은 영화 엔티티 리스트.
     */
    @Override
    @Transactional
    public List<MovieEntity> findByTitleLike(String title, Locale locale, Pageable pageable) {
        log.debug( "Searching Movies for title: {}", title );
        JPAQuery<MovieEntity> queryFindByTitleLike
                = createQueryFindByTitleLikeAndLocale( title, locale );
        
        List<MovieEntity> result = queryFindByTitleLike.limit( pageable.getPageSize() )
                                                       .offset( pageable.getOffset() )
                                                       .fetch();
        log.debug( "movie search result: {}", result.size() );
        return result;
    }
    
    @Override
    public Page<MovieEntity> findPagedByTitleLike(String title, Locale locale, Pageable pageable) {
        JPAQuery<MovieEntity> query = this.createQueryFindByTitleLikeAndLocale( title, locale );
        List<MovieEntity> all
                = query.fetch();
        List<MovieEntity> currentPageItems
                = query.limit( pageable.getPageSize() )
                       .offset( pageable.getOffset() )
                       .fetch();
        return new PageImpl<>( currentPageItems, pageable, all.size() );
    }
    
    @Override
    public Optional<MovieEntity> findByTmdbIdFetchJoin(Integer tmdbId, Locale locale) {
        log.debug( "Searching Movies for tmdbId: {}", tmdbId );
        MovieEntity result = createQueryFindByTmdbIdFetchJoin( tmdbId )
                                     .fetchOne();
        log.debug( "movie detail result" );
        return Optional.ofNullable( result );
    }
    
    private static BooleanExpression containsFromEnglishTitle(String title) {
        return movieEntity.title.englishTitle.contains( title );
    }
    
    private static BooleanExpression containsFromKoreanTitle(String title) {
        return movieEntity.title.koreanTitle
                       .contains( title );
    }
    
    private JPAQuery<MovieEntity> createQueryFindByTitleLike(String title) {
        return jpaQueryFactory
                       .select( movieEntity )
                       .from( movieEntity )
                       .innerJoin( movieEntity.title ).fetchJoin()
                       .where( containsFromKoreanTitle( title )
                                       .or( containsFromEnglishTitle( title ) )
                             );
    }
    
    private JPAQuery<MovieEntity> createQueryFindByTitleLikeAndLocale(String title, Locale locale) {
        if ( locale.equals( Locale.KOREAN ) ) {
            return createQueryFindByTitleLike( title );
        }
        else {
            return createQueryFindByTitleLikeEnglish( title );
            
        }
    }
    
    private JPAQuery<MovieEntity> createQueryFindByTitleLikeEnglish(String title) {
        return jpaQueryFactory
                       .select( movieEntity )
                       .from( movieEntity )
                       .innerJoin( movieEntity.title ).fetchJoin()
                       .where( containsFromEnglishTitle( title ) );
    }
    
    private JPAQuery<MovieEntity> createQueryFindByTmdbIdFetchJoin(Integer tmdbId) {
        return jpaQueryFactory
                       .select( movieEntity )
                       .from( movieEntity )
                       .innerJoin( movieEntity.title ).fetchJoin()
                       .where( movieEntity.tmdbId.eq( tmdbId ) );
    }
}
