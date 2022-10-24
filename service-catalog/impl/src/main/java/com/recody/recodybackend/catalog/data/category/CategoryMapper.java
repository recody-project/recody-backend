package com.recody.recodybackend.catalog.data.category;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.exceptions.UnsupportedCategoryException;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class CategoryMapper {
    
    @Autowired CategoryRepository categoryRepository;
    
    
    /**
     * 기본 카테고리를 엔티티로 변환한다.
     * DB에 없는 카테고리이면 저장 후 반환, DB에 있으면 꺼내서 반환한다.
     * @param basicCategory 기본 카테고리 객체
     * @return Category entity
     */
    public CategoryEntity map(BasicCategory basicCategory){
        log.debug("mapping basicCategory to entity");
        String categoryId = basicCategory.getId();
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(categoryId);
        CategoryEntity savedEntity;
        if (optionalCategory.isEmpty()){
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
    
    /**
     * @param entity : 카테고리의 엔티티
     * @return Category 객체. 기본 카테고리일 경우, BasicCategory 를,
     *          아닐 경우 커스텀 카테고리 객체를 반환한다.
     */
    public Category map(CategoryEntity entity) {
        if (entity.isBasic()){
            String name = entity.getName();
            return BasicCategory.of(name);
        }
        // 현재 기본카테고리 이외에는 변환할 카테고리가 없다.
        throw new UnsupportedCategoryException();
    }
    
}
