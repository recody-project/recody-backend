package com.recody.recodybackend.book;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookDetail {
    private String isbn;
    private String title;
    private String publisher;
    private String pubDate;
    private String description;

    @JsonAlias(value = {"image"})
    private String imagePath;
    private String authors;

    @Override
    public String toString() {
        return "{\"BookDetail\":{"
                + "\"isbn\":" + ((isbn != null) ? ("\"" + isbn + "\"") : null)
                + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null)
                + ", \"publisher\":" + ((publisher != null) ? ("\"" + publisher + "\"") : null)
                + ", \"pubDate\":" + ((pubDate != null) ? ("\"" + pubDate + "\"") : null)
                + ", \"description\":" + ((description != null) ? ("\"" + description + "\"") : null)
                + ", \"imagePath\":" + ((imagePath != null) ? ("\"" + imagePath + "\"") : null)
                + ", \"authors\":" + ((authors != null) ? ("\"" + authors + "\"") : null)
                + "}}";
    }
}
