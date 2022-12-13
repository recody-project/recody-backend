package com.recody.recodybackend.book.features.getbookdetail.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaverBookDetail {


    private String title;
    private String publisher;
    private String pubDate;
    private String description;

    @JsonAlias(value = {"image"})
    private String imagePath;
    private String isbn;
    private String authors;


}
