package com.recody.recodybackend.book.features.searchbooks;

import com.recody.recodybackend.book.data.book.BookEntity;
import com.recody.recodybackend.book.data.book.BookRepository;
import com.recody.recodybackend.book.features.BookSearchService;
import com.recody.recodybackend.book.features.searchbooks.dto.NaverBookSearchNode;
import com.recody.recodybackend.book.web.NaverSearchedBook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultBookService implements BookSearchService {

    public static final int MINIMUM_SEARCH_RESULT_SIZE = 0;

    private final SearchBooksHandler<NaverBookSearchNode> naverSearchBooksHandler;
    private final BookRepository bookRepository;


    @Override
    @Transactional
    public SearchBooksResult searchBooks(SearchBooks command) {
        String bookName = command.getBookName();
        List<BookEntity> bookEntities = bookRepository.findByTitleLike(bookName);


//        if (bookEntities.size() > MINIMUM_SEARCH_RESULT_SIZE) {
//            List<NaverSearchedBook> books
//        }
        List<NaverBookSearchNode> naverBooks = naverSearchBooksHandler.handle(command);



        return SearchBooksResult.builder().books();
    }
}
