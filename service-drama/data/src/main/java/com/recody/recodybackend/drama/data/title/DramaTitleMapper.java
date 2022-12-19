package com.recody.recodybackend.drama.data.title;

import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.tmdb.TMDBDrama;
import org.mapstruct.*;
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
    
    public DramaTitleEntity newEntity(TMDBDrama drama, @Context Locale locale) {
        String title = drama.getName();
        if ( locale.getLanguage().equals( Locale.KOREAN.getLanguage() ) ) {
            if ( StringUtils.hasText( title ) ) {
                return DramaTitleEntity.builder()
                                       .koreanTitle( title )
                                       .originalTitle( drama.getOriginalName() )
                                       .build();
            }
            else {
                return DramaTitleEntity.builder()
                                       .englishTitle( drama.getName() )
                                       .originalTitle( drama.getOriginalName() )
                                       .build();
            }
        }
        else {
            return DramaTitleEntity.builder()
                                   .englishTitle( title )
                                   .originalTitle( drama.getOriginalName() )
                                   .build();
        }
    }
    
    public DramaTitleEntity update(@MappingTarget DramaTitleEntity entity, TMDBDrama drama, @Context Locale locale) {
        if (locale.getLanguage().equals( Locale.KOREAN.getLanguage() )){
            entity.setKoreanTitle( drama.getName() );
            return entity;
        }
        else {
            entity.setEnglishTitle( drama.getName() );
            return entity;
        }
    }
    
    @AfterMapping
    public void setBidirectional(@MappingTarget DramaEntity drama){
        DramaTitleEntity title = drama.getTitle();
        title.setDrama( drama );
    }
}
