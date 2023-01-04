package com.recody.recodybackend.book.data.book;

import com.recody.recodybackend.book.features.getbookdetail.dto.NaverBookDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

import java.util.Locale;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true))
@RequiredArgsConstructor
@Slf4j
public abstract class BookDetailMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping( target = "lastModifiedAt", ignore = true )
    @Mapping( target = "createdAt", ignore = true )
    @Mapping(target = "authors", ignore = true)
    public abstract BookEntity update(@MappingTarget BookEntity entity, NaverBookDetail detail,
                                      @Context Locale locale);

    @Mapping(target = "id", ignore = true)
    @Mapping( target = "title", source = "args.title" )
    @Mapping( target = "authors", ignore = true )
    public abstract BookEntity newEntity(NaverBookDetail args, @Context Locale locale);


}
