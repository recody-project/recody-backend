package com.recody.recodybackend.catalog.features.eventhandlers;

import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.CategoryRepository;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.catalog.data.content.CatalogContentTitleEntity;
import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.common.OnSpringEventProcessingStrategy;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.exceptions.CategoryNotFoundException;
import com.recody.recodybackend.movie.events.MovieCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
@Conditional(value = OnSpringEventProcessingStrategy.class)
@RequiredArgsConstructor
@Slf4j
class SpringCatalogContentEventHandler implements CatalogContentEventHandler{
    
    private final CategoryRepository categoryRepository;
    private final CatalogContentRepository contentRepository;
    
    @Override
    @EventListener
    @Transactional
    @Async(value = Recody.CATALOG_TASK_EXECUTOR )
    public void on(MovieCreated event) {
        log.debug( "consuming event: {}", event );
        String contentId = event.getContentId();
        boolean exists = contentRepository.existsByContentId( contentId );
        if (exists){
            log.warn( "이미 존재하는 작품 정보입니다.: {}", contentId);
            return;
        }
    
        CategoryEntity categoryEntity = categoryRepository.findById( BasicCategory.Movie.getId() )
                                                          .orElseThrow( CategoryNotFoundException::new );
        CatalogContentTitleEntity titleEntity = CatalogContentTitleEntity.builder().koreanTitle( event.getKoreanTitle() ).englishTitle(
                event.getEnglishTitle() ).build();
        CatalogContentEntity contentEntity = CatalogContentEntity.builder()
                                                                 .category( categoryEntity )
                                                                 .contentId( event.getContentId() )
                                                                 .imageUrl( event.getPosterUrl() )
                                                                 .build();
        contentEntity.setTitle( titleEntity );
        CatalogContentEntity savedContent = contentRepository.save( contentEntity );
        log.info( "새로운 영화 작품을 Catalog 에 저장합니다.: {}", contentEntity.getContentId() );
    }
}
