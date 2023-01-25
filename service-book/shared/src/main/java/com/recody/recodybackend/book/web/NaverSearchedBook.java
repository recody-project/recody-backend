package com.recody.recodybackend.book.web;

import lombok.Data;

@Data
public class NaverSearchedBook {
    private String title;
    private String image;

    @Override
    public String toString() {
        return "{\"NaverSearchedBook\":{" + "\"title\":" + title + ", \"image\":" + ((image != null) ? ("\"" + image + "\"") : null) + "}}";
    }
}
