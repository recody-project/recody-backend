package com.recody.recodybackend.book.data.book;

import com.recody.recodybackend.book.BookInfo;
import com.recody.recodybackend.book.BookSource;
import com.recody.recodybackend.book.features.searchbooks.dto.NaverBookSearchNode;
import com.recody.recodybackend.book.web.NaverSearchedBook;
import com.recody.recodybackend.common.contents.BasicCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Locale;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        imports = {BasicCategory.class, BookSource.class})
@RequiredArgsConstructor
@Slf4j
public abstract class BookMapper {

    public abstract List<NaverSearchedBook> toNaverBook(List<NaverBookSearchNode> nodes);

    public abstract NaverSearchedBook toNaverBook(NaverBookSearchNode node);

    @Mapping( target = "category", expression = "java(BasicCategory.Book)" )
    @Mapping( target = "source", expression = "java(BookSource.Naver)" )
    @Mapping( target = "title", source = "entity.title" )
    @Mapping( target = "contentId", source = "entity.id" )
    @Mapping( target = "genres", source = "entity.genres" )
    public abstract BookInfo toBookInfo(BookEntity entity, @Context Locale locale);

}
