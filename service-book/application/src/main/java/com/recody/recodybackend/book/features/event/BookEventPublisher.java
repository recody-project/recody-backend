package com.recody.recodybackend.book.features.event;

import com.recody.recodybackend.book.events.BookCreated;
import com.recody.recodybackend.common.Recody;
import org.springframework.scheduling.annotation.Async;

public interface BookEventPublisher {

    @Async( Recody.BOOK_TASK_EXECUTOR )
    void publish(BookCreated event);

    void publish(BookFetched event);

    void publish(BookQueried event);

}
