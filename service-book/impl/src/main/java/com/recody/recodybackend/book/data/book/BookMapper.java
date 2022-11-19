package com.recody.recodybackend.book.data.book;

import com.recody.recodybackend.book.features.searchbooks.dto.NaverBookSearchNode;
import com.recody.recodybackend.book.web.NaverSearchedBook;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public abstract class BookMapper {

    public abstract List<NaverSearchedBook> toNaverBook(List<NaverBookSearchNode> nodes);

}
