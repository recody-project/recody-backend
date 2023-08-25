package com.recody.recodybackend.book.data.book;


import com.recody.recodybackend.book.data.people.AuthorEntity;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;

@Mapper( componentModel = "spring",
        builder = @Builder(disableBuilder = true))
@Slf4j
public abstract class BookPersonMapper {
    public String concatAuthors(List<AuthorEntity> entities, @Context Locale locale) {
        StringBuilder stringBuilder = new StringBuilder();
        if ( !entities.isEmpty() ) {
            for (AuthorEntity author : entities) {
                concatPersonName(stringBuilder, author.getName() );
            }
        }
        return stringBuilder.toString();
    }

    private void concatPersonName(StringBuilder stringBuilder,
                                          String name) {
        if ( !StringUtils.hasText( name ) ){
            log.warn( "이 Person 에 Name 정보가 없습니다." );
            return;
        }

        if ( stringBuilder.length() != 0 ) {
            stringBuilder.append( ", " );
        }
        stringBuilder.append( name );

    }

}
