package com.recody.recodybackend;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.ContentDetail;
import com.recody.recodybackend.features.getbookcredit.Author;
import com.recody.recodybackend.general.BookGenre;
import com.recody.recodybackend.general.BookSource;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Book implements ContentDetail {

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
    public String getContentId() {
        return contentId;
    }

    @Override
    public BasicCategory getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "{\"Book\":{" + "\"bookId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"source\":" + ((source != null) ? ("\"" + source + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"description\":" + ((description != null) ? ("\"" + description + "\"") : null) + ", \"pubDate\":" + ((pubDate != null) ? ("\"" + pubDate + "\"") : null) + ", \"popularity\":" + publisher + ", \"isbn\":" + isbn + ", \"imagePath\":" + ((imagePath != null) ? ("\"" + imagePath + "\"") : null) + ", \"genres\":" + genres + "}}";
    }
}
