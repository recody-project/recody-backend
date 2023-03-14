package com.recody.recodybackend.book.data.people;

import com.recody.recodybackend.book.data.book.BookEntity;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "BookAuthor")
@Builder
@Table(name = "book_author")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    private String name;

    @Override
    public String toString() {
        return "{\"AuthorEntity\":{" + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + ", \"book\":" + book + ", \"name\":" + name + "}}";
    }

    public void setBook(BookEntity book) {
        if (this.book != null) {
            this.book.getAuthors().remove(this);
        }
        this.book = book;
        book.getAuthors().add(this);
    }

}
