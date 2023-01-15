package com.recody.recodybackend.book.features.eventhandlers;

import com.recody.recodybackend.book.features.event.BookFetched;

public interface BookFetchedEventHandler {
    void on(BookFetched event);
}
