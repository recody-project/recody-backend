package com.recody.recodybackend.book.features.eventhandlers;

import com.recody.recodybackend.book.features.event.BookQueried;

public interface EmptyBookQueriedHandler {
    void on(BookQueried event);
}
