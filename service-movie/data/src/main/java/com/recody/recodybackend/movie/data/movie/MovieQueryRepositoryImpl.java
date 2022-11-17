package com.recody.recodybackend.movie.data.movie;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


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
        List<MovieEntity> result = new ArrayList<>();
        if ( locale.equals( Locale.KOREAN ) ) {
            result = jpaQueryFactory
                             .select( QMovieEntity.movieEntity )
                             .from( QMovieEntity.movieEntity )
                             .innerJoin( QMovieEntity.movieEntity.title ).fetchJoin()
                    
                             .where( QMovieEntity.movieEntity.title.koreanTitle.contains( title )
                                                                               .or( QMovieEntity.movieEntity.title.englishTitle.contains(
                                                                                       title ) )
                                   )
                             .limit( pageable.getPageSize() )
                             .offset( pageable.getOffset() )
                             .fetch();
        }
        else if ( locale.equals( Locale.ENGLISH ) ) {
            result = jpaQueryFactory
                    
                             .select( QMovieEntity.movieEntity )
                             .from( QMovieEntity.movieEntity )
                             .innerJoin( QMovieEntity.movieEntity.title ).fetchJoin()
                             .where( QMovieEntity.movieEntity.title.englishTitle.contains( title )
                                   )
                             .limit( pageable.getPageSize() )
                             .offset( pageable.getOffset() )
                             .fetch();
        }
        log.debug( "movie search result: {}", result.size() );
        return result;
    }
    
    @Override
    public Optional<MovieEntity> findByTmdbIdFetchJoin(Integer tmdbId, Locale locale) {
        log.debug( "Searching Movies for tmdbId: {}", tmdbId );
        MovieEntity result = null;
        if ( locale.equals( Locale.KOREAN ) ) {
            result = jpaQueryFactory
                             .select( QMovieEntity.movieEntity )
                             .from( QMovieEntity.movieEntity )
                             .innerJoin( QMovieEntity.movieEntity.title ).fetchJoin()
                    
                             .where( QMovieEntity.movieEntity.tmdbId.eq( tmdbId )
                                   )
                             .fetchOne();
        }
        else if ( locale.equals( Locale.ENGLISH ) ) {
            result = jpaQueryFactory
                             .select( QMovieEntity.movieEntity )
                             .from( QMovieEntity.movieEntity )
                             .innerJoin( QMovieEntity.movieEntity.title ).fetchJoin()
                    
                             .where( QMovieEntity.movieEntity.tmdbId.eq( tmdbId )
                                   )
                             .fetchOne();
        }
        log.debug( "movie detail result" );
        return Optional.ofNullable( result );
    }
}
