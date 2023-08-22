package com.recody.recodybackend.book.naver.detail;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class NaverBookDetail {


    private String title;
    private String publisher;
    private String pubDate;
    private String description;

    @JsonAlias(value = {"image"})
    private String imagePath;
    private String isbn;
    private String authors;

    @Override
    public String toString() {
        return "{\"NaverBookDetail\":{"
                + "\"title\":" + title
                + ", \"publisher\":" + ((publisher != null) ? ("\"" + publisher + "\"") : null)
                + ", \"pubDate\":" + ((pubDate != null) ? ("\"" + pubDate + "\"") : null)
                + ", \"description\":" + ((description != null) ? ("\"" + description + "\"") : null)
                + ", \"imagePath\":" + ((imagePath != null) ? ("\"" + imagePath + "\"") : null)
                + ", \"isbn\":" + ((isbn != null) ? ("\"" + isbn + "\"") : null)
                + ", \"authors\":" + ((authors != null) ? ("\"" + authors + "\"") : null)
                + "}}";
    }
}
