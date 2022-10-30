package com.recody.recodybackend.catalog.data.category;

import com.recody.recodybackend.catalog.features.CategoryIconUrl;
import com.recody.recodybackend.catalog.features.CategoryName;
import com.recody.recodybackend.catalog.features.CustomCategory;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Category;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper( componentModel = "spring",
         imports = {CategoryName.class, CategoryIconUrl.class}
)
@Slf4j
public abstract class CategoryMapper {
    
    @Autowired
    CategoryRepository categoryRepository;
    
    
    @Mapping( target = "basic", source = "entity.basic" )
    @Mapping( target = "name", expression = "java(CategoryName.of(entity.getName()))")
    @Mapping( target = "iconUrl", expression = "java(CategoryIconUrl.of(entity.getIconUrl()))")
    public abstract CustomCategory toCustomCategory(CategoryEntity entity);
    
    /**
     * 기본 카테고리를 엔티티로 변환한다.
     * DB에 없는 카테고리이면 저장 후 반환, DB에 있으면 꺼내서 반환한다.
     *
     * @param basicCategory 기본 카테고리 객체
     * @return Category entity
     */
    public CategoryEntity map(BasicCategory basicCategory) {
        log.debug("mapping basicCategory to entity");
        String categoryId = basicCategory.getId();
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(categoryId);
        CategoryEntity savedEntity;
        if ( optionalCategory.isEmpty() ) {
            CategoryEntity categoryEntity = CategoryEntity.builder()
                                                          .id(categoryId)
                                                          .name(basicCategory.getName())
                                                          .basic(true)
                                                          .build();
            savedEntity = categoryRepository.save(categoryEntity);
            log.info("Saved categoryEntity: {}", savedEntity);
            return savedEntity;
        }
        return optionalCategory.get();
    }
    
    @IterableMapping(qualifiedBy = GeneralCategoryMapper.class)
    public abstract List<Category> toCategory(List<CategoryEntity> entities);
    
    /**
     * @param entity : 카테고리의 엔티티
     * @return Category 객체. 기본 카테고리일 경우, BasicCategory 를,
     * 아닐 경우 커스텀 카테고리 객체를 반환한다.
     */
    @GeneralCategoryMapper
    public Category toCategory(CategoryEntity entity) {
        if ( entity.isBasic() ) {
            String id = entity.getId();
            return BasicCategory.idOf(id);
        }
        
        else {
            CustomCategory customCategory = this.toCustomCategory(entity);
            log.debug("mapping to customCategory: {}", customCategory);
            return customCategory;
        }
    }
    
}
