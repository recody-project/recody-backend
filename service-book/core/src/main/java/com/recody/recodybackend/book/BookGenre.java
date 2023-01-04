package com.recody.recodybackend.book;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.contents.Genre;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookGenre implements Genre {

    private String genreId;
    private String genreName;
    private BookSource source;


    public BookGenre(String genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }

    @Override
    public Category getCategory() {
        return BasicCategory.Book;
    }

    @Override
    public String toString() {
        return "{\"BookGenre\":{" + "\"genreId\":" + genreId + ", \"genreName\":" + ((genreName != null) ? ("\"" + genreName + "\"") : null) + "}}";
    }
}
