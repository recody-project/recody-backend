package com.recody.recodybackend.book.features.synchronizebookdetail;

import com.recody.recodybackend.book.BookId;
import com.recody.recodybackend.book.data.book.BookEntity;
import com.recody.recodybackend.book.data.book.BookRepository;
import com.recody.recodybackend.book.features.fetchbookdetail.FetchBookDetail;
import com.recody.recodybackend.book.features.fetchbookdetail.FetchBookDetailHandler;
import com.recody.recodybackend.book.features.registerbookdetail.RegisterBookDetail;
import com.recody.recodybackend.book.features.registerbookdetail.RegisterBookDetailHandler;
import com.recody.recodybackend.book.naver.detail.NaverBookDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultSynchronizeBookDetailHandler implements SynchronizeBookDetailHandler<Void> {

    private final RegisterBookDetailHandler<BookEntity> registerBookDetailHandler;
    private final BookRepository bookRepository;
    private final FetchBookDetailHandler<NaverBookDetail> fetchBookDetailHandler;


    @Override
    public Void handle(BookId id) {
        String idValue = id.getValue();
        Optional<BookEntity> optionalBook = bookRepository.findById(idValue);
        if (optionalBook.isEmpty()) {
            log.warn( "책을 찾을 수 없습니다. {}", idValue );
            return null;
        }
        String isbn = optionalBook.get().getIsbn();
        NaverBookDetail detail = fetchBookDetailHandler.handle(
                FetchBookDetail.builder()
                        .locale(Locale.KOREAN)
                        .isbn(isbn)
                        .build());
        registerBookDetailHandler.handle(
                RegisterBookDetail.builder()
                        .detail(detail)
                        .locale(Locale.KOREAN)
                        .build());
        log.trace( "locale {} 에 대한 상세정보 저장 완료.", Locale.KOREAN );

        return null;
    }
}
