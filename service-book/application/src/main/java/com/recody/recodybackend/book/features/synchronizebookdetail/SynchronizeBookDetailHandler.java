package com.recody.recodybackend.book.features.synchronizebookdetail;

import com.recody.recodybackend.book.BookId;

public interface SynchronizeBookDetailHandler<R> {
    R handle(BookId id);
}
