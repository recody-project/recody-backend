package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.content.ContentId;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentMapper;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.content.CatalogContent;
import com.recody.recodybackend.content.CatalogMovie;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.common.exceptions.UnsupportedCategoryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultContentLoader implements ContentLoader<CatalogContentEntity>{
    
    private final CatalogContentRepository contentRepository;
    private final CatalogContentMapper catalogContentMapper;
    
    @Override
    @Transactional
    public CatalogContent load(ContentId contentIdObject, Locale locale) {
        log.debug("loading content with contentId: {}", contentIdObject);
        String contentId = contentIdObject.getContentId();
        CatalogContentEntity contentEntity
                = contentRepository.findByContentId(contentId)
                                   .orElseThrow(ContentNotFoundException::new);
        BasicCategory basicCategory = BasicCategory.idOf(contentEntity.getCategory().getId());
        if (basicCategory.equals(BasicCategory.Movie)){
            CatalogMovie catalogMovie = catalogContentMapper.toCatalogMovie(contentEntity, locale);
            log.debug("returning catalogMovie: {}", catalogMovie);
            return catalogMovie;
        }
        throw new UnsupportedCategoryException();
    }
    
    @Override
    @Transactional
    public CatalogContentEntity loadEntity(ContentId contentIdObject) {
        log.debug("loading content entity with contentId: {}", contentIdObject);
        String contentId = contentIdObject.getContentId();
        CatalogContentEntity contentEntity
                = contentRepository.findByContentId(contentId)
                                   .orElseThrow(ContentNotFoundException::new);
        log.debug( "loaded contentEntity: {}", contentEntity.getContentId() );
        return contentEntity;
    }
}
