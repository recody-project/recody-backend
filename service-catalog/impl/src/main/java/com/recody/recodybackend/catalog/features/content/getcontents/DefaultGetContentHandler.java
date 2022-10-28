package com.recody.recodybackend.catalog.features.content.getcontents;

import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.CategoryMapper;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentMapper;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.contents.Content;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.common.exceptions.UnsupportedCategoryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetContentHandler implements GetContentHandler {
    
    private final CatalogContentRepository contentRepository;
    private final CatalogContentMapper contentMapper;
    private final CategoryMapper categoryMapper;
    
    @Override
    @Transactional
    public Content<?> handle(GetContent command) {
        log.debug("handling command: {}", command);
        String contentId = command.getContentId().getContentId();
        BasicCategory basicCategory = command.getContentId().parseCategory();
        CategoryEntity mappedCategory = categoryMapper.map(basicCategory);
    
        CatalogContentEntity catalogContentEntity
                = contentRepository.findByContentIdAndCategory(contentId, mappedCategory)
                                   .orElseThrow(ContentNotFoundException::new);
        
        Content<?> content;
        CategoryEntity categoryEntity = catalogContentEntity.getCategory(); // 영속성에 없으면 쿼리 나감
        Category category = categoryMapper.toCategory(categoryEntity);
        
        if ( BasicCategory.Movie.equals(category) ) {
            content = contentMapper.toCatalogMovie(catalogContentEntity);
        }
        else {
            throw new UnsupportedCategoryException();
        }
        return content;
    }
}
