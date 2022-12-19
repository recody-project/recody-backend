package com.recody.recodybackend.drama.data.title;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Mapper( componentModel = "spring" )
public abstract class DramaTitleMapper {
    
    public String map(DramaTitleEntity entity, @Context Locale locale) {
        if ( locale.getLanguage().equals( Locale.KOREAN.getLanguage() ) ) {
            String koreanTitle = entity.getKoreanTitle();
            if ( StringUtils.hasText( koreanTitle ) ) {
                return koreanTitle;
            }
            else {
                return entity.getOriginalTitle();
            }
        }
        else {
            String englishTitle = entity.getEnglishTitle();
            if ( StringUtils.hasText( englishTitle ) ) {
                return englishTitle;
            }
            else {
                return entity.getOriginalTitle();
            }
        }
    }
}
