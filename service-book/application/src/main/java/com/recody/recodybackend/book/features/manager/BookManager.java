package com.recody.recodybackend.book.features.manager;

import com.recody.recodybackend.book.features.getbookdetail.dto.NaverBookDetail;
import com.recody.recodybackend.book.naver.NaverBook;

public interface BookManager extends DetailedBookRegistrar<NaverBookDetail> {

    BookRegistrar<NaverBook> book();

}
