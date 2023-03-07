package com.recody.recodybackend.book.features.searchbooks;

import lombok.*;

import java.util.Locale;

/**
 * 책 검색 정보.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchBooks {

    private String keyword;

    private Locale locale;

    @Builder.Default
    private Integer page = 1;
}
