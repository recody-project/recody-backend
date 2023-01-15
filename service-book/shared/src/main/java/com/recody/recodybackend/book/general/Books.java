package com.recody.recodybackend.book.general;

import com.recody.recodybackend.book.Book;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Books {

    private List<Book> books;

    public Books(List<Book> books) {
        this.books = books;
    }

    public static Books of(List<Book> books) {
        return new Books(books);
    }
}
