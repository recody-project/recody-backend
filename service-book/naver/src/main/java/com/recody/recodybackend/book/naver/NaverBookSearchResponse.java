package com.recody.recodybackend.book.naver;

import com.recody.recodybackend.book.searchbooks.dto.NaverBookSearchNode;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NaverBookSearchResponse {
    private Integer total;

    private List<NaverBook> items;

    @Override
    public String toString() {
        return "{\"NaverBookSearchResponse\":{" + "\"total\":" + total + ", \"items\":" + items + "}}";
    }
}
