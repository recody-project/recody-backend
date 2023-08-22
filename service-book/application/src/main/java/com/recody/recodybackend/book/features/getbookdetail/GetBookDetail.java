package com.recody.recodybackend.book.features.getbookdetail;

import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetBookDetail {
    private String bookId;
    private Locale locale;

    @Override
    public String toString() {
        return "{\"GetBookDetail\":{"
                + "\"bookId\":" + ((bookId != null) ? ("\"" + bookId + "\"") : null)
                + ", \"locale\":" + locale
                + "}}";
    }
}
