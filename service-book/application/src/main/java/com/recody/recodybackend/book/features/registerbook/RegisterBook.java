package com.recody.recodybackend.book.features.registerbook;

import com.recody.recodybackend.book.naver.NaverBook;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterBook {
    private NaverBook book;

    @Override
    public String toString() {
        return "{\"RegisterBook\":{"
                + "\"book\":" + book
                + "}}";
    }
}
