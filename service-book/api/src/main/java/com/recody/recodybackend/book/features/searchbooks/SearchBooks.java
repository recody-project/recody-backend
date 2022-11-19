package com.recody.recodybackend.book.features.searchbooks;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

/**
 * 책 검색 정보.
 */
@Data
@Builder
public class SearchBooks {

    private String bookName;
    @Builder.Default
    private Integer display = 10;
    @Builder.Default
    private Integer start = 1;
}
