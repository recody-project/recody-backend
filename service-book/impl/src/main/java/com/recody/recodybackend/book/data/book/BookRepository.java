package com.recody.recodybackend.book.data.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, String> {

    List<BookEntity> findByTitleLike(String title);
    Optional<BookEntity> findByIsbn(String isbn);


}
