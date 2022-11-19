package com.recody.recodybackend.catalog.data.category;

import com.recody.recodybackend.common.contents.BasicCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({"dev", "local", "test"})
public class CategoryRegistrar {
    
    private final CategoryRepository categoryRepository;
    
    @PostConstruct
    void register(){
        List<BasicCategory> all = BasicCategory.all();
        for (BasicCategory category : all) {
            Optional<CategoryEntity> optionalCategory
                    = categoryRepository.findById( category.getId() );
            if (optionalCategory.isEmpty()){
                CategoryEntity entity = CategoryEntity.builder()
                                                      .id( category.getId() )
                                                      .basic( true )
                                                      .name( category.getName() ).build();
                categoryRepository.save( entity );
                log.info( "기본 카테고리 등록함: {}", category );
            }
        }
    }
    
}
