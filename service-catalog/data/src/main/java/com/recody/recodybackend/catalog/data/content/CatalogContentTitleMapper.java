package com.recody.recodybackend.catalog.data.content;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.Locale;

@Mapper(componentModel = "spring")
public abstract class CatalogContentTitleMapper {
    
    public CatalogContentTitleEntity newEntity(String title, @Context Locale locale){
        if (locale.equals( Locale.KOREAN )){
            return CatalogContentTitleEntity.builder()
                    .koreanTitle( title ).build();
        } else {
            return CatalogContentTitleEntity.builder()
                                            .englishTitle( title ).build();
        }
        // 다른언어는 현재 지원하지 않음.
    }
    
    public String toTitle(CatalogContentTitleEntity title, @Context Locale locale){
        if (locale.equals( Locale.KOREAN )){
            return title.getKoreanTitle();
        } else {
            return title.getEnglishTitle();
        }
        // 다른언어는 현재 지원하지 않음.
    }
    
    
}
