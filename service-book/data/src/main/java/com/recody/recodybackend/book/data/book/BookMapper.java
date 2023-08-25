package com.recody.recodybackend.book.data.book;

import com.recody.recodybackend.book.BookDetail;
import com.recody.recodybackend.book.BookInfo;
import com.recody.recodybackend.book.BookSource;
import com.recody.recodybackend.book.naver.NaverBook;
import com.recody.recodybackend.book.naver.detail.NaverBookDetail;
import com.recody.recodybackend.book.searchbooks.dto.NaverBookSearchNode;
import com.recody.recodybackend.book.Book;
import com.recody.recodybackend.book.web.NaverSearchedBook;
import com.recody.recodybackend.common.contents.BasicCategory;
import org.mapstruct.*;

import java.util.List;
import java.util.Locale;

@Mapper( componentModel = "spring",
        uses = {BookPersonMapper.class},
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

    @Mapping( target = "id", ignore = true )
    @Mapping( target = "genres", ignore = true)
    @Mapping(target = "authors", ignore = true)
    public abstract BookEntity newEntity(NaverBookDetail book, @Context Locale locale);

    @Mapping( target = "title", source = "entity.title")
    @Mapping( target = "contentId", source = "entity.id" )
    @Named("defaultE2D")
    public abstract Book map(BookEntity entity, @Context Locale locale);

    @IterableMapping(qualifiedByName = "defaultE2D")
    public abstract List<Book> map(List<BookEntity> entities, @Context Locale locale);

    @Mapping( target = "id", ignore = true )
    public abstract void update(@MappingTarget BookEntity entity, NaverBook book, @Context Locale locale);

    @Mapping( target = "id", ignore = true )
    @Mapping( target = "genres", ignore = true)
    @Mapping(target = "authors", ignore = true)
    public abstract void updateDetail(@MappingTarget BookEntity entity, NaverBookDetail detail, @Context Locale locale);

//    public abstract void updateAuthor(@MappingTarget BookEntity entity, NaverBookDetail detail);

//    @AfterMapping
//    public void updateAuthor(NaverBookDetail detail, @MappingTarget BookEntity entity) {
//        entity.setAuthors();
//    }

//    public List<AuthorEntity> mapAuthor(String author){
//        String[] authors = author.split(";");
//    }


    public abstract BookDetail toBookDetail(BookEntity entity, @Context Locale locale);


}
