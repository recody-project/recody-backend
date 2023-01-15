package com.recody.recodybackend.book.features.searchbookfromnaver;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchBookFromNaver {

    private String queryText;

    private String language;

    private Integer page;

    @Builder.Default
    private Integer display = 10;
    @Builder.Default
    private Integer start = 1;
}
