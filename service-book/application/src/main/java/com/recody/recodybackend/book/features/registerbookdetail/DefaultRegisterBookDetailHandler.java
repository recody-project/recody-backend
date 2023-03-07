package com.recody.recodybackend.book.features.registerbookdetail;

import com.recody.recodybackend.book.data.book.BookEntity;
import com.recody.recodybackend.book.data.book.BookMapper;
import com.recody.recodybackend.book.data.book.BookRepository;
import com.recody.recodybackend.book.naver.detail.NaverBookDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultRegisterBookDetailHandler implements RegisterBookDetailHandler<BookEntity> {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public BookEntity handle(RegisterBookDetail command) {
        log.trace( "handling command: {}", command );
        NaverBookDetail detail = command.getDetail();
        log.trace("NaverBookDetail : {}", detail.toString());
        String isbn = detail.getIsbn();
        Locale locale = command.getLocale();
        // 이 때의 Book 은 보통 저장되어있습니다. (검색 이벤트를 통해 저장됨.)
        // 저장되어 있지 않은 경우, 새로운 Book 을 저장한 후 Detail 을 반영합니다.
        Optional<BookEntity> optionalBookEntity = bookRepository.findByIsbn(isbn);
        BookEntity bookEntity;

        if (optionalBookEntity.isEmpty()) {
            BookEntity newBookEntity = bookMapper.newEntity(detail, locale);
            bookEntity = bookRepository.save(newBookEntity);
            log.trace("saved new Book: {}", newBookEntity);
        } else {
            bookEntity = optionalBookEntity.get();
        }
        bookMapper.updateDetail(bookEntity, detail, locale);
        log.trace( "updated bookEntity: {}", bookEntity );

        return bookEntity;
    }
}
