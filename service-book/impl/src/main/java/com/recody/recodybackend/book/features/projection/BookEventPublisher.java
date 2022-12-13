package com.recody.recodybackend.book.features.projection;

import com.recody.recodybackend.book.events.BookCreated;
import com.recody.recodybackend.common.Recody;
import org.springframework.scheduling.annotation.Async;

public interface BookEventPublisher {

    @Async( Recody.BOOK_TASK_EXECUTOR )
    void publish(BookCreated event);

}
