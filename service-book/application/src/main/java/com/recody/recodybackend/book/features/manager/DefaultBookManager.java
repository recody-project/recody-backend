package com.recody.recodybackend.book.features.manager;

import com.recody.recodybackend.book.Book;
import com.recody.recodybackend.book.BookInfo;
import com.recody.recodybackend.book.data.book.BookDetailMapper;
import com.recody.recodybackend.book.data.book.BookEntity;
import com.recody.recodybackend.book.data.book.BookMapper;
import com.recody.recodybackend.book.data.book.BookRepository;
import com.recody.recodybackend.book.events.BookCreated;
import com.recody.recodybackend.book.features.getbookdetail.dto.NaverBookDetail;
import com.recody.recodybackend.book.features.event.BookEventPublisher;
import com.recody.recodybackend.book.naver.NaverBook;
import com.recody.recodybackend.book.searchbooks.dto.NaverBookSearchNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultBookManager implements BookManager{

    private final BookEventPublisher bookEventPublisher;
    private final BookRepository bookRepository;

    private final BookRegistrar<NaverBook> bookRegistrar;
    private final BookDetailMapper bookDetailMapper;
    private final BookMapper bookMapper;

    private void publishNewBookCreated(BookEntity savedEntity) {
        BookCreated event = BookCreated.builder()
                .contentId(savedEntity.getId())
                .imagePath(savedEntity.getImagePath())
                .title(savedEntity.getTitle())
                .build();
        bookEventPublisher.publish(event);

    }

    @Override
    @Transactional
    public BookInfo register(NaverBookDetail source, Locale locale) {
        log.debug( "registering NaverBookDetail");
        Optional<BookEntity> optionalBookEntity = bookRepository.findByIsbn(source.getIsbn());
        if (optionalBookEntity.isPresent()) {
            BookEntity bookEntity = optionalBookEntity.get();
            BookEntity updateEntity = bookDetailMapper.update(bookEntity, source, locale);
            BookInfo book = bookMapper.toBookInfo(updateEntity, locale);
            log.info( "책을 업데이트 후 반환합니다. book: {}", book );
            return book;
        }
        BookEntity bookEntity = bookDetailMapper.newEntity(source, locale);
        log.debug( "new bookEntity: {}", bookEntity );
        BookEntity savedEntity = bookRepository.save(bookEntity);
        log.debug( "savedEntity: {}", savedEntity );

        publishNewBookCreated(savedEntity);
        BookInfo book = bookMapper.toBookInfo(savedEntity, locale);
        log.info( "책을 등록하였습니다. book: {}", book );


        return book;
    }

    @Override
    public BookRegistrar<NaverBook> book() {
        return bookRegistrar;
    }

    @Component
    @RequiredArgsConstructor
    @Slf4j
    public static class ConcreteBookRegistrar implements BookRegistrar<NaverBook> {

        private final BookRepository bookRepository;


        @Override
        public List<Book> register(List<NaverBook> sources, Locale locale) {
            log.info( " {} 개의 영화를 저장합니다. ", sources.size() );
            return sources.stream().map( source -> this.register( source, locale ) ).collect( Collectors.toList() );
        }

        @Override
        @Transactional
        public Book register(NaverBook source, Locale locale) {
            log.debug( "registering NaverBook, locale: {}", locale );
            Optional<BookEntity> optionalBook;
            String title = source.getTitle();
            optionalBook = bookRepository.findByIsbn(source.getIsbn());

            if (optionalBook.isPresent()) {
                BookEntity currentBookEntity = optionalBook.get();
//                BookEntity updatedBookEntity=
            }

            return null;
        }

    }


}
