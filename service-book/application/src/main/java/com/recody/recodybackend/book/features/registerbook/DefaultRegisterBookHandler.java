package com.recody.recodybackend.book.features.registerbook;

import com.recody.recodybackend.book.data.book.BookEntity;
import com.recody.recodybackend.book.data.book.BookMapper;
import com.recody.recodybackend.book.data.book.BookRepository;
import com.recody.recodybackend.book.data.people.AuthorEntity;
import com.recody.recodybackend.book.data.people.BookAuthorRepository;
import com.recody.recodybackend.book.naver.NaverBook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultRegisterBookHandler implements RegisterBookHandler<BookEntity> {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BookAuthorRepository authorRepository;

    @Override
    public BookEntity handle(RegisterBook command) {
        log.trace( "handling command: {}", command );
        NaverBook book = command.getBook();
        Optional<BookEntity> optionalBook = bookRepository.findByIsbn( book.getIsbn() );
        // entity 로 데이터를 저장한다.
        BookEntity bookEntity;

        if ( optionalBook.isEmpty() ) {
            BookEntity newEntity = bookMapper.newEntity( book, Locale.KOREAN );
            String[] authors = book.getAuthor().split("\\^");
            bookEntity = bookRepository.save( newEntity );
            for (String author : authors) {
                AuthorEntity authorEntity = AuthorEntity.builder().name(author).build();
                authorEntity.setBook(bookEntity);
                authorRepository.save(authorEntity);
            }
        }
        else {
            bookEntity = optionalBook.get();
            bookMapper.update( bookEntity, book, Locale.KOREAN );
        }
        return bookEntity;


    }
}
