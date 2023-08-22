package com.recody.recodybackend.book.features.synchronizebooksearchresults;

import com.recody.recodybackend.book.BookSearchKeyword;
import com.recody.recodybackend.book.data.book.BookEntity;
import com.recody.recodybackend.book.features.registerbook.RegisterBook;
import com.recody.recodybackend.book.features.registerbook.RegisterBookHandler;
import com.recody.recodybackend.book.features.searchbookfromnaver.SearchBookFromNaver;
import com.recody.recodybackend.book.features.searchbookfromnaver.SearchBookFromNaverHandler;
import com.recody.recodybackend.book.naver.NaverBook;
import com.recody.recodybackend.book.naver.NaverBookSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;



@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSynchronizeBookSearchResultsHandler implements SynchronizeBookSearchResultsHandler<Void>{

    private final SearchBookFromNaverHandler<NaverBookSearchResponse> searchBookFromNaverHandler;

    private final RegisterBookHandler<BookEntity> registerBookHandler;

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
            registerBookHandler.handle(RegisterBook.builder()
                    .book(book).build());
        }

        return null;
    }
}
