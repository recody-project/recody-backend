package com.recody.recodybackend.book.features.searchbooks.dto;

import lombok.Data;

import java.util.List;

@Data
public class NaverBookSearchResponse {
    private Integer total;
    private Integer start;
    private Integer display;
    private List<NaverBookSearchNode> items;

    @Override
    public String toString() {
        return "{\"NaverBookSearchResponse\":{" + "\"total\":" + total + ", \"start\":" + start + ", \"display\":" + display + ", \"items\":" + items + "}}";
    }
}
