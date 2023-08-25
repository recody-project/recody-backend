package com.recody.recodybackend.book.features.fetchbookdetail;

import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FetchBookDetail {
    private String isbn;

    private Locale locale;

    @Override
    public String toString() {
        return "{\"FetchBookDetail\":{"
                + "\"isbn\":" + ((isbn != null) ? ("\"" + isbn + "\"") : null)
                + ", \"locale\":" + locale
                + "}}";
    }
}
