package com.recody.recodybackend.book;

import lombok.Getter;

@Getter
public class SearchingKeyword {
    private final String value;

    public SearchingKeyword(String value) {
        this.value = value;
    }

    public static SearchingKeyword of(String keyword) {
        return new SearchingKeyword(keyword);
    }
}
