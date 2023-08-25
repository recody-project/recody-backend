package com.recody.recodybackend.book.features.registerbookdetail;

import com.recody.recodybackend.book.naver.detail.NaverBookDetail;
import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterBookDetail {
    private NaverBookDetail detail;
    private Locale locale;
}
