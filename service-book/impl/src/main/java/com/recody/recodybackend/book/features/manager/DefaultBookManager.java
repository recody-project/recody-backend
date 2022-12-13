package com.recody.recodybackend.book.features.manager;

import com.recody.recodybackend.book.Book;
import com.recody.recodybackend.book.BookInfo;
import com.recody.recodybackend.book.data.book.BookEntity;
import com.recody.recodybackend.book.data.book.BookRepository;
import com.recody.recodybackend.book.events.BookCreated;
import com.recody.recodybackend.book.features.getbookdetail.dto.NaverBookDetail;
import com.recody.recodybackend.book.features.projection.BookEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultBookManager implements BookManager{

    private final BookEventPublisher bookEventPublisher;
    private final BookRepository bookRepository;

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
            BookEntity updateEntity=
        }




        return null;
    }

}
