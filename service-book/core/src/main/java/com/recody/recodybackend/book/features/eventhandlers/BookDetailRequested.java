package com.recody.recodybackend.book.features.eventhandlers;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookDetailRequested {
    private String bookId;

    @Override
    public String toString() {
        return "{\"BookDetailRequested\":{"
                + "\"bookId\":" + ((bookId != null) ? ("\"" + bookId + "\"") : null)
                + "}}";
    }

}
