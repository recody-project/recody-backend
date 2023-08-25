package com.recody.recodybackend.book.features.fetchbookdetail;

public interface FetchBookDetailHandler<R> {
    R handle(FetchBookDetail query);
}
