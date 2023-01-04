package com.recody.recodybackend.drama.data.overview;

import com.recody.recodybackend.drama.data.drama.DramaEntity;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class DramaOverviewMapper {
    
    public DramaOverviewEntity newEntity(String overview, @Context Locale locale) {
        DramaOverviewEntity.DramaOverviewEntityBuilder builder = DramaOverviewEntity.builder();
        if ( !StringUtils.hasText( overview ) ) {
            log.trace( "mapped empty overview: {}", overview );
            return builder.build();
        }
        if ( locale.getLanguage().equals( Locale.KOREAN.getLanguage() ) ) {
            log.trace( "mapped overview: {}", overview );
            return builder.koreanOverview( overview ).build();
        }
        else {
            log.trace( "mapped overview: {}", overview );
            return builder.englishOverview( overview ).build();
        }
    }
    
    public void update(@MappingTarget DramaOverviewEntity entity, String overview, @Context Locale locale){
        if (!StringUtils.hasText( overview )){
            log.warn( "returning because of empty overview, entity: {}", entity );
            return;
        }
        if ( locale.getLanguage().equals( Locale.KOREAN.getLanguage() ) ) {
            entity.setKoreanOverview( overview );
        }
        else {
            entity.setEnglishOverview( overview );
        }
        
    }
    
    @AfterMapping
    public void setBidirectional(@MappingTarget DramaEntity drama) {
        DramaOverviewEntity overview = drama.getOverview();
        overview.setDrama( drama );
    }
}
