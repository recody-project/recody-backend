package com.recody.recodybackend.book.features.searchbooks.dto;

import com.recody.recodybackend.book.web.NaverSearchedBook;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring",
builder = @Builder(disableBuilder = true))
public abstract class BookMapper {

    public abstract List<NaverSearchedBook> toNaverBook(List<NaverBookSearchNode> nodes);

    public abstract NaverSearchedBook toNaverBook(NaverBookSearchNode node);



}
