package com.recody.recodybackend.book.data.search;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookSearchingKeywordCountRepository extends JpaRepository<BookSearchingKeywordCountEntity, Long> {
    Optional<BookSearchingKeywordCountEntity> findFirstByText(String text);
}
