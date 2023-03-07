package com.recody.recodybackend.book.data.people;

import com.recody.recodybackend.book.data.book.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookAuthorRepository extends JpaRepository<AuthorEntity, Long> {

    Optional<AuthorEntity> findByBookAndName(BookEntity book, String name);
}
