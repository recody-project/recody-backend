package com.recody.recodybackend.book.web;

import lombok.Data;

@Data
public class NaverSearchedBook {
    private String title;
    private String imagePath;

    @Override
    public String toString() {
        return "{\"NaverSearchedBook\":{" + "\"title\":" + title + ", \"imagePath\":" + ((imagePath != null) ? ("\"" + imagePath + "\"") : null) + "}}";
    }
}
