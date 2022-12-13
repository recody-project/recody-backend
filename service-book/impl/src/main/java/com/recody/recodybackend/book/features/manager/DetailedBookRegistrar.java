package com.recody.recodybackend.book.features.manager;

import com.recody.recodybackend.book.Book;
import com.recody.recodybackend.book.BookInfo;
import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.common.contents.register.AsyncContentRegistrar;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public interface DetailedBookRegistrar<T> extends AsyncContentRegistrar<BookInfo, T> {

    @Override
    @Async(Recody.BOOK_TASK_EXECUTOR)
    @Transactional
    default CompletableFuture<BookInfo> registerAsync(T t, Locale locale) {
        return AsyncContentRegistrar.super.registerAsync(t, locale);
    }

    @Override
    @Async(Recody.BOOK_TASK_EXECUTOR)
    @Transactional
    default CompletableFuture<List<BookInfo>> registerAsync(List<T> source, Locale locale) {
        return AsyncContentRegistrar.super.registerAsync(source, locale);
    }
}
