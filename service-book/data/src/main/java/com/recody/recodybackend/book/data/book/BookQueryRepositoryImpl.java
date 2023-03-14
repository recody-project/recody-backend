package com.recody.recodybackend.book.data.book;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private JPAQuery<BookEntity> createQueryWhereTitleLike(String keyword) {
        return jpaQueryFactory.selectFrom(bookEntity)
                .where(bookEntity.title.contains(keyword));
    }
}
