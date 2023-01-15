package com.recody.recodybackend.book.features.synchronizebooksearchresults;

import com.recody.recodybackend.book.BookSearchKeyword;

public interface SynchronizeBookSearchResultsHandler<R> {
    R handle(BookSearchKeyword keyword);
}