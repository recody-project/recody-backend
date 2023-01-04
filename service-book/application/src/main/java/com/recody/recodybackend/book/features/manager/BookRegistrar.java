package com.recody.recodybackend.book.features.manager;

import com.recody.recodybackend.book.Book;
import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.common.contents.register.AsyncContentRegistrar;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public interface BookRegistrar<T> extends AsyncContentRegistrar<Book, T> {

    @Override
    @Transactional
    Book register(T source, Locale locale);

    @Override
    @Async(Recody.BOOK_TASK_EXECUTOR)
    @Transactional
    default CompletableFuture<Book> registerAsync(T t, Locale locale) {
        return AsyncContentRegistrar.super.registerAsync(t, locale);
    }

    @Override
    @Async(Recody.BOOK_TASK_EXECUTOR)
    @Transactional
    default CompletableFuture<List<Book>> registerAsync(List<T> source, Locale locale) {
        return AsyncContentRegistrar.super.registerAsync(source, locale);
    }
}
