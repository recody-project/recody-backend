package com.recody.recodybackend.book.data.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookQueryRepository {
    List<BookEntity> findByTitleLike(String keyword, Pageable pageable);
    Page<BookEntity> findPagedByTitleLike(String keyword, Pageable pageable);
}
