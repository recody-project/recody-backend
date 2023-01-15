package com.recody.recodybackend.book.features.searchbooks;

public interface SearchBookHandler<R> {
    R handle(SearchBooks query);
}
