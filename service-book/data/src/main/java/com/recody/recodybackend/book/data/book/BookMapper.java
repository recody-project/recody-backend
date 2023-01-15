package com.recody.recodybackend.book.data.book;

import com.recody.recodybackend.book.BookInfo;
import com.recody.recodybackend.book.BookSource;
import com.recody.recodybackend.book.naver.NaverBook;
import com.recody.recodybackend.book.searchbooks.dto.NaverBookSearchNode;
import com.recody.recodybackend.book.Book;
import com.recody.recodybackend.book.web.NaverSearchedBook;
import com.recody.recodybackend.common.contents.BasicCategory;
import org.mapstruct.*;

import java.util.List;
import java.util.Locale;

@Mapper( componentModel = "spring",
        imports = {BasicCategory.class, BookSource.class},
        builder = @Builder(disableBuilder = true))
public abstract class BookMapper {

    public abstract List<NaverSearchedBook> toNaverBook(List<NaverBookSearchNode> nodes);

    public abstract NaverSearchedBook toNaverBook(NaverBookSearchNode node);

    @Mapping( target = "source", expression = "java(BookSource.Naver)" )
    @Mapping( target = "title", source = "entity.title" )
    @Mapping( target = "contentId", source = "entity.id" )
    @Mapping( target = "genres", source = "entity.genres" )
    @Mapping(target = "authors", ignore = true)
    public abstract BookInfo toBookInfo(BookEntity entity, @Context Locale locale);

    @Mapping( target = "id", ignore = true )
    public abstract BookEntity newEntity(NaverBook book, @Context Locale locale);

    @Mapping( target = "title", source = "entity.title")
    @Mapping( target = "contentId", source = "entity.id" )
    @Named("defaultE2D")
    public abstract Book map(BookEntity entity, @Context Locale locale);

    @IterableMapping(qualifiedByName = "defaultE2D")
    public abstract List<Book> map(List<BookEntity> entities, @Context Locale locale);

    @Mapping( target = "id", ignore = true )
    public abstract void update(@MappingTarget BookEntity entity, NaverBook book, @Context Locale locale);


}
