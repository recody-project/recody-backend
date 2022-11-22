package com.recody.recodybackend.catalog.data.record;

import com.recody.recodybackend.catalog.data.category.CategoryMapper;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentMapper;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.catalog.data.content.CatalogContentTitleMapper;
import com.recody.recodybackend.catalog.data.user.CatalogUserMapper;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.record.Record;
import com.recody.recodybackend.record.RecordContent;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@Mapper(componentModel = "spring", uses = {CatalogContentMapper.class, CategoryMapper.class, CatalogContentTitleMapper.class,
                                           CatalogUserMapper.class})
@Slf4j
public abstract class RecordMapper {
    
    @Autowired
    CatalogContentRepository contentRepository;
    
    @Mapping( target = "userId", source = "entity.user.id" )
    @Mapping( target = "contentId", source = "entity.content.contentId")
    public abstract Record map(RecordEntity entity);
    
    @Mapping( target = "title", source = "contentEntity", qualifiedByName = "titleMapper")
    @Mapping( target = "recordId", source = "recordEntity.recordId" )
    @Mapping( target = "lastModifiedAt", source = "recordEntity.lastModifiedAt")
    public abstract RecordContent map(CatalogContentEntity contentEntity, RecordEntity recordEntity, @Context Locale locale);
    
    @Named("contentMapper")
    public CatalogContentEntity map(String contentId) {
        return contentRepository.findByContentId(contentId).orElseThrow(ContentNotFoundException::new);
    }
    
    @Named( "titleMapper" )
    String mapTitle(CatalogContentEntity entity, @Context Locale locale){
        String koreanTitle = entity.getTitle().getKoreanTitle();
        String englishTitle = entity.getTitle().getEnglishTitle();
        
        if ( locale.equals( Locale.KOREAN ) ) {
            if (koreanTitle == null){
                log.debug( "한국어 타이틀이 없어서 영어 타이들을 매핑: {}", englishTitle );
                return englishTitle;
            }
            return koreanTitle;
        }
        else {
            if (englishTitle == null){
                log.debug( "영어 타이틀이 없어서 한국어 타이들을 매핑: {}", koreanTitle );
                return koreanTitle;
            }
            return englishTitle;
        }
    }
}
