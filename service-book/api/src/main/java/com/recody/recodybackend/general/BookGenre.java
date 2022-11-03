package com.recody.recodybackend.general;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Genre;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookGenre implements Genre {

    private Integer genreId;
    private String genreName;
    private BookSource source;

    public BookGenre() {}

    public BookGenre(Integer genreId) {
        this.genreId = genreId;
    }

    public BookGenre(Integer genreId, String genreName) {
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
