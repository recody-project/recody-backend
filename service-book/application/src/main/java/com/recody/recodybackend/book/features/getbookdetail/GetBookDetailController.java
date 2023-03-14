package com.recody.recodybackend.book.features.getbookdetail;

import com.recody.recodybackend.book.BookDetail;
import com.recody.recodybackend.common.web.SuccessResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
class GetBookDetailController {

    private final GetBookDetailHandler<BookDetail> getBookDetailHandler;
    private final MessageSource messageSource;

    @GetMapping("/api/v1/book/{bookId}/detail")
    public SuccessResponseBody getBookDetail(@PathVariable String bookId, HttpServletRequest request) {
        return SuccessResponseBody.builder()
                .message(messageSource.getMessage("book.detail.succeeded",
                        null,
                        "책 상세정보를 반환합니다.",
                        request.getLocale()))
                .data(getBookDetailHandler.handle(
                        GetBookDetail.builder()
                                .bookId(bookId)
                                .locale(request.getLocale())
                                .build()))
                .build();
    }

    @GetMapping("/api/v1/book/{bookId}/detail-data")
    public BookDetail getBookDetailData(@PathVariable String bookId,
                                          HttpServletRequest request) {
        return getBookDetailHandler.handle(
                GetBookDetail.builder()
                        .bookId( bookId )
                        .locale( request.getLocale() )
                        .build() );
    }
}
