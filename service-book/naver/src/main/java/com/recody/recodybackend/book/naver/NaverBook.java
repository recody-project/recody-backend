package com.recody.recodybackend.book.naver;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverBook {

    private String title;
    @JsonAlias(value = {"image"})
    private String imagePath;
    private String author;
    private String discount;
    private String publisher;
    @JsonAlias("pubdate")
    private String pubDate;
    private String isbn;
    private String description;

    @Override
    public String toString() {
        return "{\"NaverBook\":{" +
                "title='" + title + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", author='" + author + '\'' +
                ", discount='" + discount + '\'' +
                ", publisher='" + publisher + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", isbn='" + isbn + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
