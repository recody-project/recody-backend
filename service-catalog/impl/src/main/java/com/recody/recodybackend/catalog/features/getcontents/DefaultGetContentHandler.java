package com.recody.recodybackend.catalog.features.getcontents;

import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentMapper;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.contents.Content;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import com.recody.recodybackend.common.exceptions.UnsupportedCategoryException;
import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetContentHandler implements GetContentHandler{
    
    private final CatalogContentRepository contentRepository;
    private final CatalogContentMapper contentMapper;
    private final CategoryMapper categoryMapper;
    
    @Override
    public Content handle(GetContent command) {
        log.debug("handling command: {}", command);
        String contentId = command.getContentId();
        CatalogContentEntity catalogContentEntity = contentRepository
                                                            .findByContentId(contentId)
                                                            .orElseThrow(ContentNotFoundException::new);
        Content content;
        CategoryEntity categoryEntity = catalogContentEntity.getCategory();
        Category category = categoryMapper.map(categoryEntity);
        if (BasicCategory.Movie.equals(category)) {
            content = contentMapper.mapToMovie(catalogContentEntity);
        } else {
            throw new UnsupportedCategoryException();
        }
        return content;
    }
}
