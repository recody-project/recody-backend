package com.recody.recodybackend.book.features.getbookdetail;

import com.recody.recodybackend.book.BookDetail;
import com.recody.recodybackend.book.data.book.BookEntity;
import com.recody.recodybackend.book.data.book.BookMapper;
import com.recody.recodybackend.book.data.book.BookRepository;
import com.recody.recodybackend.book.features.eventhandlers.BookDetailRequested;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetBookDetailHandler implements GetBookDetailHandler<BookDetail> {
    private final ApplicationEventPublisher applicationEventPublisher;

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public BookDetail handle(GetBookDetail query) {
        log.trace( "handling query: {}", query );
        String bookId = query.getBookId();
        applicationEventPublisher.publishEvent( new BookDetailRequested( bookId ) );
        BookEntity bookEntity = bookRepository.findById( bookId )
                .orElseThrow( ContentNotFoundException::new );
        BookDetail bookDetail = bookMapper.toBookDetail( bookEntity, query.getLocale() );
        log.debug( "bookDetail 을 반환합니다.: {}", bookDetail );
        return bookDetail;
    }
}
