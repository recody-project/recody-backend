package com.recody.recodybackend.book.web;

import com.recody.recodybackend.book.features.BookSearchService;
import com.recody.recodybackend.book.features.searchbooks.SearchBooks;
import com.recody.recodybackend.book.features.searchbooks.SearchBooksResult;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final MessageSource ms;

    private final BookSearchService bookSearchService;

    @GetMapping("/api/v1/book/search")
    public ResponseEntity<SearchBooksResult> search(@RequestParam String bookName) {
        return ResponseEntity.ok(
                bookSearchService.searchBooks(
                        SearchBooks.builder()
                                .bookName(bookName)
                                .build()
                )
        );
    }

    @GetMapping("/api/v2/book/search")
    public ResponseEntity<SuccessResponseBody> search2(@RequestParam String bookName, HttpServletRequest request) {
        return ResponseEntity.ok(SuccessResponseBody.builder()
                .message(ms.getMessage("book.search.succeeded", null, request.getLocale()))
                .data(bookSearchService.searchBooks(
                        SearchBooks.builder()
                                .bookName(bookName)
                                .build()
                ))
                .build()

        );
    }

}
