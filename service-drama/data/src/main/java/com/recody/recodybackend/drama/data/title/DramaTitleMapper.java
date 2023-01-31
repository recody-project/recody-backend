package com.recody.recodybackend.drama.data.title;

import com.recody.recodybackend.drama.data.drama.DramaEntity;
import com.recody.recodybackend.drama.tmdb.TMDBDrama;
import com.recody.recodybackend.drama.tmdb.detail.TMDBDramaDetail;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Mapper( componentModel = "spring" )
@Slf4j
public abstract class DramaTitleMapper {
    
    // entity -> title String by locale
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
    
    // drama -> new title entity
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
    
    // drama -> title update
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
    
    // drama detail -> new title entity
    public DramaTitleEntity newEntity(TMDBDramaDetail detail, @Context Locale locale) {
        if (detail == null){
            return null;
        }
        DramaTitleEntity.DramaTitleEntityBuilder builder = DramaTitleEntity.builder();
        builder.originalTitle( detail.getOriginalName() );
        if ( locale.getLanguage().equals( Locale.KOREAN.getLanguage() ) ) {
            log.trace( "mapped korean title: {}", detail.getName() );
            return builder.koreanTitle( detail.getName() ).build();
        }
        else {
            log.trace( "mapped english title: {}", detail.getName() );
            return builder.englishTitle( detail.getName() ).build();
        }
    }
    
    
    // detail -> title update
    public void update(@MappingTarget DramaTitleEntity entity, TMDBDramaDetail detail,
                       @Context Locale locale) {
        if (locale.getLanguage().equals( Locale.KOREAN.getLanguage() )){
            entity.setKoreanTitle( detail.getName() );
        }
        else {
            entity.setEnglishTitle( detail.getName() );
        }
    }
    
    @AfterMapping
    public void setBidirectional(@MappingTarget DramaEntity drama){
        DramaTitleEntity title = drama.getTitle();
        title.setDrama( drama );
    }
}
