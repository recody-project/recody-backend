package com.recody.recodybackend.book.features.event;

import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookQueried {
    private String keyword;

    @Override
    public String toString() {
        return "{\"EmptyBookQueried\":{"
                + "\"keyword\":" + ((keyword != null) ? ("\"" + keyword + "\"") : null)
                + "}}";
    }
}
