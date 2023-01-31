package com.recody.recodybackend.book.data.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "book_genre")
public class BookGenreEntity extends BookBaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "book_genre_contains_book_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BookEntity book;

    private String genreCode;

    @Override
    public String toString() {
        return "[{\"BookGenreEntity\":{" + "\"id\":" + id + ", \"book\":" + book + ", \"genreCode\":" + genreCode + "}}, " + super.toString() + "]";
    }


}
