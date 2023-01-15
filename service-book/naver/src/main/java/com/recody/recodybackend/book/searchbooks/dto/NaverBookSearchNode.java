package com.recody.recodybackend.book.searchbooks.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class NaverBookSearchNode {
    private String title;
    private String image;
    private String author;
    private String discount;
    private String publisher;
    @JsonAlias("pubdate")
    private String pubDate;
    private String isbn;
    private String description;

    @Override
    public String toString() {
        return "{\"NaverBookSearchNode\":{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", discount='" + discount + '\'' +
                ", publisher='" + publisher + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", isbn='" + isbn + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
