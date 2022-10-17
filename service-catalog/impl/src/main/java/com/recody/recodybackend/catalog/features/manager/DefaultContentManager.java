package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentMapper;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.catalog.features.projection.ContentEventPublisher;
import com.recody.recodybackend.common.contents.Content;
import com.recody.recodybackend.common.events.ContentCreated;
import com.recody.recodybackend.common.exceptions.UnsupportedCategoryException;
import com.recody.recodybackend.movie.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultContentManager implements ContentManager {
    
    private final CatalogContentRepository contentRepository;
    private final CatalogContentMapper mapper;
    
    private final ContentEventPublisher contentEventPublisher;
    
    @Override
    public <T extends Content> String register(T content) {
        log.debug("registering content: {}", content);
        if (content instanceof Movie){
            Movie movie = (Movie) content;
            Optional<CatalogContentEntity> optionalContent = contentRepository.findByContentId(movie.getMovieId());
            if (optionalContent.isPresent()){
                return optionalContent.get().getId();
            }
            CatalogContentEntity contentEntity = mapper.map(movie);
            log.debug("created contentEntity: {}", contentEntity);
            CatalogContentEntity savedContent = contentRepository.save(contentEntity);
            String catalogId = savedContent.getId();
            log.info("Catalog 에 새로운 작품이 등록됩니다. {}", catalogId);
            ContentCreated event = createEvent(savedContent);
            contentEventPublisher.publish(event);
            return catalogId;
        } else {
            log.warn("영화가 아님. content: {}", content);
            throw new UnsupportedCategoryException();
        }
    }
    
    private ContentCreated createEvent(CatalogContentEntity entity) {
        CategoryEntity categoryEntity = entity.getCategory();
        return ContentCreated.builder()
                             .catalogId(entity.getId())
                       .contentId(entity.getContentId())
                       .title(entity.getTitle())
                       .imageUrl(entity.getImageUrl())
                       .categoryId(categoryEntity.getId())
                       .categoryName(categoryEntity.getName())
                             .build();
    }
}
