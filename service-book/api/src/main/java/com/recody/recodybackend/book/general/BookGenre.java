package com.recody.recodybackend.book.general;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Genre;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookGenre implements Genre {

    private String genreId;
    private String genreName;
    private BookSource source;

    public BookGenre(String genreId) {
        this.genreId = genreId;
    }

    public BookGenre(String genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }

    @Override
    public BasicCategory getCategory() {
        return BasicCategory.Book;
    }

    @Override
    public String toString() {
        return "{\"BookGenre\":{" + "\"genreId\":" + genreId + ", \"genreName\":" + ((genreName != null) ? ("\"" + genreName + "\"") : null) + "}}";
    }
}
