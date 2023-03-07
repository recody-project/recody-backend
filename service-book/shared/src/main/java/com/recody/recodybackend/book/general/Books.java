package com.recody.recodybackend.book.general;

import com.recody.recodybackend.book.Book;
import com.recody.recodybackend.common.data.QueryMetadata;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Books {

    private QueryMetadata metadata;

    private List<Book> books;

    public Books(List<Book> books) {
        this.books = books;
    }

    public Books(List<Book> books, QueryMetadata metadata) {
        this.books = books;
        this.metadata = metadata;
    }

    public static Books of(List<Book> books) {
        return new Books(books);
    }
    public static Books of(List<Book> books, QueryMetadata queryMetadata) {
        return new Books(books, queryMetadata);
    }

}
