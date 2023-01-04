package com.recody.recodybackend.book.features.eventhandlers;

import com.recody.recodybackend.book.data.book.BookMapper;
import com.recody.recodybackend.book.data.book.BookRepository;
import com.recody.recodybackend.book.data.search.BookSearchingKeywordCountEntity;
import com.recody.recodybackend.book.data.search.BookSearchingKeywordCountRepository;
import com.recody.recodybackend.book.features.event.BookFetched;
import com.recody.recodybackend.book.features.event.BookQueried;
import com.recody.recodybackend.common.Recody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SpringBookFetchedEventHandler implements BookFetchedEventHandler, EmptyBookQueriedHandler{

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BookSearchingKeywordCountRepository keywordRepository;

    @Override
    @Async( value = Recody.BOOK_TASK_EXECUTOR )
    @EventListener
    @Transactional
    public void on(BookQueried event) {
        log.debug( "handling event: {}", event );
        Optional<BookSearchingKeywordCountEntity> optionalCount = keywordRepository.findFirstByText(event.getKeyword());
        if (optionalCount.isPresent()) {
            log.trace("optionalCount is present");
            BookSearchingKeywordCountEntity bookSearchingKeywordEntity = optionalCount.get();
            bookSearchingKeywordEntity.increment();
            int count = bookSearchingKeywordEntity.getCount() % 5;
            if (count != 0) {
                return;
            }
        } else {
            keywordRepository.save(BookSearchingKeywordCountEntity.builder()
                    .text(event.getKeyword())
                    .build());
        }
    }

    @Override
    public void on(BookFetched event) {

    }


}
