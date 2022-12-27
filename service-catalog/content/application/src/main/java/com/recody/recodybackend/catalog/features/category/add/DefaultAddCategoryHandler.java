package com.recody.recodybackend.catalog.features.category.add;

import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserRepository;
import com.recody.recodybackend.category.CustomCategory;
import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.CategoryMapper;
import com.recody.recodybackend.catalog.data.category.CategoryRepository;
import com.recody.recodybackend.exceptions.CustomCategoryException;
import com.recody.recodybackend.users.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultAddCategoryHandler implements AddCategoryHandler {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CatalogUserRepository userRepository;
    
    @Override
    @Transactional
    public CustomCategory handle(AddCategory command) {
        log.debug( "handling command: {}", command );
        Long userId = command.getUserId();
        String name = command.getName().getName();
        String iconUrl = command.getIconUrl().getIconUrl();
    
        CatalogUserEntity userEntity = userRepository.findById( userId ).orElseThrow( UserNotFoundException::new );
    
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findByNameAndUserId( name, userId );
    
        if ( optionalCategoryEntity.isPresent() ) {
            throw new CustomCategoryException( CustomCategory.CustomCategoryErrorType.CustomCategoryAlreadyExists );
        }
    
        int count = categoryRepository.countByUserIdAndBasicIsFalse( userId );
    
        if ( count >= CustomCategory.MAX_CUSTOM_CATEGORIES ) {
            throw new CustomCategoryException( CustomCategory.CustomCategoryErrorType.CannotOver5CustomCategories );
        }
    
        CategoryEntity entity = newCategoryEntity( userEntity, name, iconUrl );
        CategoryEntity savedEntity = categoryRepository.save( entity );
        CustomCategory customCategory = categoryMapper.toCustomCategory( savedEntity );
        log.debug( "new Custom Category.: {}", customCategory );
        log.info("커스텀 카테고리를 추가하였습니다.: {}", customCategory);
        
        return customCategory;
    }
    
    private static CategoryEntity newCategoryEntity(CatalogUserEntity user, String name, String iconUrl) {
        return CategoryEntity.builder().iconUrl( iconUrl ).user( user ).name( name ).build();
    }
}
