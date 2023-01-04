package com.recody.recodybackend.book.features.searchbooks;

import java.util.List;

public interface SearchBooksHandler<T> {
    List<T> handle(SearchBooks command);
}
