package com.recody.recodybackend.book;

public class BookId {
    private final String value;

    public BookId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BookId of(String value) {
        return new BookId( value );
    }

    @Override
    public String toString() {
        return "{\"BookId\":{"
                + "\"value\":" + ((value != null) ? ("\"" + value + "\"") : null)
                + "}}";
    }
}
