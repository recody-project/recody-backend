package com.recody.recodybackend.book.features.registerbook;

public interface RegisterBookHandler<R> {
    R handle(RegisterBook command);
}
