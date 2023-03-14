package com.recody.recodybackend.book.features.event;

import com.recody.recodybackend.book.naver.NaverBook;
import lombok.*;

import java.util.List;
import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookFetched {
    private List<NaverBook> books;
    private Locale locale;

    @Override
    public String toString() {
        return "{\"DramaFetched\":{"
                + "\"dramas size\":" + books.size()
                + ", \"locale\":" + locale
                + "}}";
    }
}
