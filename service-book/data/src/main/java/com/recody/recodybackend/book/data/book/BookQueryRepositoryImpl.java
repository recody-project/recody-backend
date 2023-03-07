package com.recody.recodybackend.book.data.book;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.recody.recodybackend.book.data.book.QBookEntity.bookEntity;

@Slf4j
@RequiredArgsConstructor
public class BookQueryRepositoryImpl implements BookQueryRepository{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<BookEntity> findByTitleLike(String keyword, Pageable pageable) {
        log.debug( "keyword: {}, pageable: {}", keyword, pageable );
        JPAQuery<BookEntity> query;
        query = createQueryWhereTitleLike(keyword);
        log.debug( "query: {}", query );
        if (!pageable.isUnpaged()) {
            query.limit(pageable.getPageSize())
                    .offset(pageable.getOffset());
        }

        return query.fetch();
    }

    @Override
    public Page<BookEntity> findPagedByTitleLike(String keyword, Pageable pageable) {
        JPAQuery<BookEntity> wholeQuery = createQueryWhereTitleLike(keyword);
        List<BookEntity> wholeResults = wholeQuery.fetch();

        JPAQuery<BookEntity> pagedQuery = applyPageable(wholeQuery, pageable);
        List<BookEntity> pagedResults = pagedQuery.fetch();
        return new PageImpl<>(pagedResults, pageable, wholeResults.size());
    }

    private JPAQuery<BookEntity> createQueryWhereTitleLike(String keyword) {
        return jpaQueryFactory.selectFrom(bookEntity)
                .where(bookEntity.title.contains(keyword));
    }

    private static JPAQuery<BookEntity> applyPageable(JPAQuery<BookEntity> query, Pageable pageable) {
        if ( !pageable.isUnpaged() ) {
            return query.limit( pageable.getPageSize() )
                    .offset( pageable.getOffset() );
        }
        else {
            return query;
        }
    }
}
