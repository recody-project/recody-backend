package com.recody.recodybackend.drama.data.people;

import com.recody.recodybackend.drama.tmdb.credit.TMDBDramaCast;
import com.recody.recodybackend.drama.tmdb.credit.TMDBDramaCrew;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Mapper( componentModel = "spring" )
@Slf4j
public abstract class DramaPersonNameMapper {
    
    public DramaPersonNameEntity newEntity(TMDBDramaCrew crew, @Context Locale locale) {
        DramaPersonNameEntity.DramaPersonNameEntityBuilder builder = DramaPersonNameEntity.builder();
        builder.originalName( crew.getOriginalName() );
        if ( !StringUtils.hasText( crew.getName() ) ) {
            log.trace( "mapped empty person name: {}", crew.getName() );
            return builder.build();
        }
        if ( locale.getLanguage().equals( Locale.KOREAN.getLanguage() ) ) {
            log.trace( "mapped person name: {}", crew.getName() );
            return builder.koreanName( crew.getName() ).build();
        }
        else {
            log.trace( "mapped person name: {}", crew.getName() );
            return builder.englishName( crew.getName() ).build();
        }
    }
    
    public DramaPersonNameEntity newEntity(TMDBDramaCast crew, @Context Locale locale) {
        DramaPersonNameEntity.DramaPersonNameEntityBuilder builder = DramaPersonNameEntity.builder();
        builder.originalName( crew.getOriginalName() );
        if ( !StringUtils.hasText( crew.getName() ) ) {
            log.trace( "mapped empty person name: {}", crew.getName() );
            return builder.build();
        }
        if ( locale.getLanguage().equals( Locale.KOREAN.getLanguage() ) ) {
            log.trace( "mapped person name: {}", crew.getName() );
            return builder.koreanName( crew.getName() ).build();
        }
        else {
            log.trace( "mapped person name: {}", crew.getName() );
            return builder.englishName( crew.getName() ).build();
        }
    }

//    public DramaPersonNameEntity newEntity(TMDBDramaCreatedBy createdBy, @Context Locale locale) {
//        DramaPersonNameEntity.DramaPersonNameEntityBuilder builder = DramaPersonNameEntity.builder();
//        builder.originalName( createdBy.getOriginalName() );
//        if ( !StringUtils.hasText( createdBy.getName() ) ) {
//            log.trace( "mapped empty person name: {}", createdBy.getName() );
//            return builder.build();
//        }
//        if (locale.getLanguage().equals( Locale.KOREAN.getLanguage() )){
//            log.trace( "mapped person name: {}", createdBy.getName() );
//            return builder.koreanName( createdBy.getName() ).build();
//        }
//        else {
//            log.trace( "mapped person name: {}", createdBy.getName() );
//            return builder.englishName( createdBy.getName() ).build();
//        }
//    }
    
    // NameEntity 를 Locale 에 따라 String 으로 반환한다.
    public String map(DramaPersonNameEntity entity, @Context Locale locale) {
        String koreanName = entity.getKoreanName();
        String englishName = entity.getEnglishName();
        String originalName = entity.getOriginalName();
        
        if ( locale.getLanguage().equals( Locale.KOREAN.getLanguage() ) ) {
            if ( StringUtils.hasText( koreanName ) ) {
                return koreanName;
            }
            if ( StringUtils.hasText( englishName ) ) {
                return englishName;
            }
            if ( StringUtils.hasText( originalName ) ) {
                return originalName;
            }
            return "";
        }
        else {
            if ( StringUtils.hasText( englishName ) ) {
                return englishName;
            }
            if ( StringUtils.hasText( koreanName ) ) {
                return koreanName;
            }
            if ( StringUtils.hasText( originalName ) ) {
                return originalName;
            }
            return "";
        }
    }
    
    @AfterMapping
    public void setBidirectional(@MappingTarget DramaPersonEntity drama) {
        DramaPersonNameEntity name = drama.getName();
        name.setPerson( drama );
    }
}
