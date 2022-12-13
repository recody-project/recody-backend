package com.recody.recodybackend.book.data.book;

import com.recody.recodybackend.book.features.getbookdetail.dto.NaverBookDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Context;
import org.mapstruct.MappingTarget;

import java.util.Locale;

@RequiredArgsConstructor
@Slf4j
public abstract class BookDetailMapper {


    public abstract BookEntity update(@MappingTarget BookEntity entity, NaverBookDetail detail,
                                      @Context Locale locale);


}
