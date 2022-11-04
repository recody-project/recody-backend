package com.recody.recodybackend.record.data.content;

import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.events.ContentCreated;
import com.recody.recodybackend.record.RecordContent;
import com.recody.recodybackend.record.data.category.EmbeddableCategory;
import jdk.jfr.Name;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.Locale;

@Mapper(componentModel = "spring", imports = {
        EmbeddableCategory.class
})
public interface RecordContentMapper {
    
    @Mapping(target = "records", ignore = true)
    @Mapping(target = "category", expression = "java(new EmbeddableCategory(event.getCategoryId(), event.getCategoryName()))")
    @Mapping(target = "id", source = "event.catalogId")
    RecordContentEntity map(ContentCreated event);
    
    
    @Mapping( target = "title", source = "entity", qualifiedByName = "titleMapper")
    RecordContent map(RecordContentEntity entity, LocalDate appreciationDate, @Context Locale locale);
    
    /**
     * 카테고리 정보를 임베더블 카테고리로 매핑한다.
     * 기본 카테고리인 경우, id 를
     * @param category 도메인 Category
     * @return EmbeddableCategory: 저장하기 위한 카테고리
     */
    default EmbeddableCategory map(Category category) {
        String id = category.getId();
        String name = category.getName();
        return new EmbeddableCategory(id, name);
    }
    
    @Named( "titleMapper" )
    default String mapTitle(RecordContentEntity entity, @Context Locale locale){
        if ( locale.equals( Locale.KOREAN ) ) {
            return entity.getKoreanTitle();
        }
        else {
            return entity.getEnglishTitle();
        }
    }
}
