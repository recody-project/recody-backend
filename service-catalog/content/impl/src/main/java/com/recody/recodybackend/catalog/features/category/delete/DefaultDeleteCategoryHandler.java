package com.recody.recodybackend.catalog.features.category.delete;

import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.CategoryRepository;
import com.recody.recodybackend.exceptions.CategoryNotFoundException;
import com.recody.recodybackend.event.CategoryDeleted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
@Slf4j
class DefaultDeleteCategoryHandler implements DeleteCategoryHandler {
    
    private final CategoryRepository categoryRepository;
    
    @Override
    @Transactional
    public CategoryDeleted handle(DeleteCategory command) {
        log.debug( "handling command: {}", command );
        String value = command.getCustomCategoryId()
                              .getCategoryId();
        Long userId = command.getUserId();
        CategoryEntity categoryEntity = categoryRepository.findByIdAndUserId( value, userId )
                                                          .orElseThrow( CategoryNotFoundException::new );
        categoryEntity.delete();
        LocalDateTime deletedAt = categoryEntity.getDeletedAt();
        String categoryId = categoryEntity.getId();
        CategoryDeleted CategoryDeletedEvent = CategoryDeleted.builder()
                                               .deleteAt( deletedAt )
                                               .categoryId( categoryId )
                                               .build();
        
        log.debug( "CategoryDeletedEvent: {}", CategoryDeletedEvent );
        log.info( "커스텀 카테고리가 삭제되었습니다." );
        return CategoryDeletedEvent;
    }
}
