package com.recody.recodybackend.catalog.features.manager;

import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.category.CategoryMapper;
import com.recody.recodybackend.catalog.data.category.CategoryRepository;
import com.recody.recodybackend.exceptions.CategoryNotFoundException;
import com.recody.recodybackend.CatalogUser;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultCategoryManager implements CategoryManager {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    
    @Override
    @Transactional
    public Category load(String categoryId, CatalogUser user) {
        Optional<CategoryEntity> optionalEntity = categoryRepository.findById( categoryId );
        if ( optionalEntity.isPresent() ) {
            CategoryEntity categoryEntity = optionalEntity.get();
            Category category = categoryMapper.toCategory( categoryEntity );
            log.debug( "카테고리 반환 category: {}", category );
            
            return category;
        }
        throw new CategoryNotFoundException();
    }
    
    @Override
    public List<Category> loadAll(CatalogUser user) {
        log.debug( "loading category for user: {}", user );
        Long userId = user.getUserId();
        
        List<Category> categories = BasicCategory.allAsCategories();
        
        // 유저가 등록한 카테고리만 포함된다.
        List<CategoryEntity> entities = categoryRepository.findAllByUserId( userId );
        List<Category> customCategory = categoryMapper.toCategory( entities );
        
        // 기본 카테고리 + 커스텀 카테고리 를 반환한다.
        categories.addAll( customCategory );
        log.debug( "{} 개의 카테고리 반환 ", categories.size() );
        return categories;
    }
}
