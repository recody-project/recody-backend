package com.recody.recodybackend.book.features.searchbookfromnaver;


public interface SearchBookFromNaverHandler<R> {
    R handle(SearchBookFromNaver query);
}
