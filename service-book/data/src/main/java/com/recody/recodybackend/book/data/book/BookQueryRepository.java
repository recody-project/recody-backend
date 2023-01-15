package com.recody.recodybackend.book.data.book;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookQueryRepository {
    List<BookEntity> findByTitleLike(String keyword, Pageable pageable);
}
