package com.recody.recodybackend.book;

import lombok.Getter;

@Getter
public class BookSearchKeyword {
    private final String value;

    public BookSearchKeyword(String value) {
        this.value = value;
    }

    public static BookSearchKeyword of(String keyword) {
        return new BookSearchKeyword(keyword);
    }
}
