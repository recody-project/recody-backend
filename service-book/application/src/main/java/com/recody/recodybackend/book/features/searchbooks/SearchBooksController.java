package com.recody.recodybackend.book.features.searchbooks;

import com.recody.recodybackend.book.general.Books;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class SearchBooksController {

    private final SearchBookHandler<Books> searchBookHandler;
    private final MessageSource ms;

    @GetMapping("/api/v1/book/search")
    public SuccessResponseBody searchBooks(@RequestParam String keyword, HttpServletRequest request,
                                           @RequestParam(required = false, defaultValue = "1") Integer page) {
        return SuccessResponseBody.builder()
                .message(ms.getMessage("book.search.succeeded", null, null, request.getLocale()))
                .data(searchBookHandler.handle(
                        SearchBooks.builder()
                                .keyword(keyword)
                                .locale(request.getLocale())
                                .page(page)
                                .build(
                                )))
                .build();
    }

    @GetMapping("/api/v1/book/search-data")
    public Books searchBooksData(@RequestParam String keyword, HttpServletRequest request) {
        return searchBookHandler.handle(
                SearchBooks.builder()
                        .keyword(keyword)
                        .locale(request.getLocale())
                        .build());
    }

}
