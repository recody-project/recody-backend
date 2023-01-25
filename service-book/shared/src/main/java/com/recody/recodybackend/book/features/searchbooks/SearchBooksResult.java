package com.recody.recodybackend.book.features.searchbooks;

import com.recody.recodybackend.book.web.NaverSearchedBook;
import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchBooksResult {

    private List<NaverSearchedBook> books;
    private Integer total;
    private Integer page;

    @Override
    public String toString() {
        return "{\"SearchBooksResult\":{" + ", \"books\":" + books + ", \"page\":" + page + ", \"total\":" + total + "}}";
    }
}
