package com.recody.recodybackend.book.features.synchronizebooksearchresults;

import com.recody.recodybackend.book.BookSearchKeyword;
import com.recody.recodybackend.book.data.book.BookEntity;
import com.recody.recodybackend.book.data.book.BookMapper;
import com.recody.recodybackend.book.data.book.BookRepository;
import com.recody.recodybackend.book.features.manager.BookManager;
import com.recody.recodybackend.book.features.searchbookfromnaver.SearchBookFromNaver;
import com.recody.recodybackend.book.features.searchbookfromnaver.SearchBookFromNaverHandler;
import com.recody.recodybackend.book.naver.NaverBook;
import com.recody.recodybackend.book.naver.NaverBookSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSynchronizeBookSearchResultsHandler implements SynchronizeBookSearchResultsHandler<Void>{

    private final SearchBookFromNaverHandler<NaverBookSearchResponse> searchBookFromNaverHandler;

    private final BookRepository bookRepository;

    private final BookManager bookManager;

    private final BookMapper bookMapper;
    @Override
    @Transactional
    public Void handle(BookSearchKeyword keyword) {
        log.debug( "synchronizing search keyword: {}", keyword.getValue() );
        NaverBookSearchResponse response = searchBookFromNaverHandler.handle(
                SearchBookFromNaver.builder()
                        .queryText(keyword.getValue())
                        .build()
        );
        List<NaverBook> books = response.getItems();
        for (NaverBook book : books) {
            Optional<BookEntity> optionalBook = bookRepository.findByIsbn(book.getIsbn());
            if (optionalBook.isEmpty()) {
                BookEntity bookEntity = bookMapper.newEntity(book, Locale.KOREAN);
                bookRepository.save(bookEntity);
            } else {
                bookMapper.update(optionalBook.get(), book, Locale.KOREAN);
            }
        }

        return null;
    }
}
