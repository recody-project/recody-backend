package com.recody.recodybackend.book;



import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.BasicContent;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.Objects;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Book implements BasicContent {

    private String contentId;
    @Builder.Default
    private BasicCategory category = BasicCategory.Book;

    private String title;

    private String imagePath;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getContentId(), book.getContentId()) && Objects.equals(getCategory(),
                book.getCategory());
    }

    @Override
    public String toString() {
        return "{\"Book\":{" + "\"bookId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"imagePath\":" + ((imagePath != null) ? ("\"" + imagePath + "\"") : null) + "}}";
    }
}
