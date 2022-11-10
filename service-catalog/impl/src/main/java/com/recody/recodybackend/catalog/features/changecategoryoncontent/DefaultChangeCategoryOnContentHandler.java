package com.recody.recodybackend.catalog.features.changecategoryoncontent;

import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.PersonalizedCategoryEntity;
import com.recody.recodybackend.catalog.data.category.PersonalizedCategoryRepository;
import com.recody.recodybackend.catalog.data.category.CategoryRepository;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.exceptions.CategoryNotFoundException;
import com.recody.recodybackend.common.events.CategoryPersonalized;
import com.recody.recodybackend.common.exceptions.ContentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultChangeCategoryOnContentHandler implements ChangeCategoryOnContentHandler {
    
    private final PersonalizedCategoryRepository personalizedCategoryRepository;
    
    private final CatalogContentRepository contentRepository;
    
    private final CategoryRepository categoryRepository;
    
    @Override
    @Transactional
    public CategoryPersonalized handle(ChangeCategoryOnContent command) {
        log.debug( "handling command: {}", command );
        String categoryId = command.getCategoryId().getCategoryId();
        String contentId = command.getContentId().getContentId();
        Long userId = command.getUserId();
        
        CatalogContentEntity contentEntity = contentRepository.findByContentId( contentId )
                                                              .orElseThrow( ContentNotFoundException::new );
        
        CategoryEntity categoryEntity = categoryRepository.findById( categoryId )
                                                          .orElseThrow( CategoryNotFoundException::new );
        
        Optional<PersonalizedCategoryEntity> optionalPersonalizedCategory
                = personalizedCategoryRepository.findByUserIdAndContent( userId, contentEntity );
        
        if ( optionalPersonalizedCategory.isPresent() ) {
            PersonalizedCategoryEntity personalizedCategoryEntity = optionalPersonalizedCategory.get();
            personalizedCategoryEntity.setCategory( categoryEntity );
            CategoryPersonalized event = createEvent( contentId, userId, categoryEntity.getId() );
            log.info( " 개인화된 카테고리가 수정되었습니다. " );
            return event;
        }
        PersonalizedCategoryEntity personalizedCategoryEntity = PersonalizedCategoryEntity.builder()
                                                                                          .userId( userId )
                                                                                          .category( categoryEntity )
                                                                                          .content( contentEntity )
                                                                                          .build();
        PersonalizedCategoryEntity savedPersonalizedCategoryEntity
                = personalizedCategoryRepository.save( personalizedCategoryEntity );
        
        Long userId1 = savedPersonalizedCategoryEntity.getUserId();
        String categoryId1 = savedPersonalizedCategoryEntity.getCategory().getId();
        String contentId1 = savedPersonalizedCategoryEntity.getContent().getContentId();
        CategoryPersonalized event = createEvent( contentId1, userId1, categoryId1 );
    
        log.info( " 개인화된 카테고리가 적용되었습니다. " );
        return event;
    }
    
    private static CategoryPersonalized createEvent(String contentId, Long userId, String categoryId) {
        return CategoryPersonalized.builder()
                                   .categoryId( categoryId )
                                   .contentId( contentId )
                                   .userId( userId )
                                   .build();
    }
}
