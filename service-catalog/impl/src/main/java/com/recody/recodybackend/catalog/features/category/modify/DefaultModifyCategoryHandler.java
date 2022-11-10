package com.recody.recodybackend.catalog.features.category.modify;

import com.recody.recodybackend.category.CustomCategory;
import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.CategoryMapper;
import com.recody.recodybackend.catalog.data.category.CategoryRepository;
import com.recody.recodybackend.exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultModifyCategoryHandler implements ModifyCategoryHandler {
    
    private final CategoryRepository categoryRepository;
    
    private final CategoryMapper categoryMapper;
    
    @Override
    @Transactional
    public CustomCategory handle(ModifyCategory command) {
        log.debug( "handling command: {}", command );
        String categoryId = command.getCategoryId()
                                   .getCategoryId();
        Long userId = command.getUserId();
        CategoryEntity categoryEntity = categoryRepository.findByIdAndUserId( categoryId, userId )
                                                          .orElseThrow( CategoryNotFoundException::new );
    
        String name = command.getCategoryName().getName();
        String iconUrl = command.getCategoryIconUrl().getIconUrl();
        
        categoryEntity.setName( name );
        categoryEntity.setIconUrl( iconUrl );
    
        CustomCategory customCategory = categoryMapper.toCustomCategory( categoryEntity );
    
        log.info( "커스텀 카테고리를 수정하였습니다.: {}", customCategory );
        return customCategory;
    }
}
