package com.recody.recodybackend.book;

import com.recody.recodybackend.common.contents.BasicCategory;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookInfo extends Book{

    private String contentId;
    @Builder.Default
    private BasicCategory category = BasicCategory.Book;

    private BookSource source;
    private String title;
    private String publisher;
    private String pubDate;
    private String description;

    /* Details */
    private String imagePath;
    private String isbn;
    private List<BookGenre> genres;
    private List<Author> authors;

    @Override
    public String toString() {
        return "{\"BookInfo\":{" + "\"bookId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"source\":" + ((source != null) ? ("\"" + source + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"description\":" + ((description != null) ? ("\"" + description + "\"") : null) + ", \"pubDate\":" + ((pubDate != null) ? ("\"" + pubDate + "\"") : null) + ", \"popularity\":" + publisher + ", \"isbn\":" + isbn + ", \"imagePath\":" + ((imagePath != null) ? ("\"" + imagePath + "\"") : null) + ", \"genres\":" + genres + "}}";
    }
}
