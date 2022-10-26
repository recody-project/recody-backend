package com.recody.recodybackend.catalog.features.category.getmycategories;

import com.recody.recodybackend.catalog.features.CatalogUser;
import com.recody.recodybackend.catalog.features.manager.CatalogUserManager;
import com.recody.recodybackend.catalog.features.manager.CategoryManager;
import com.recody.recodybackend.common.contents.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultGetMyCategoriesHandler implements GetMyCategoriesHandler {
    
    private final CategoryManager categoryManager;
    private final CatalogUserManager catalogUserManager;
    
    @Override
    public List<Category> handle(GetMyCategories command) {
        log.debug("handling command: {}", command);
        Long userId = command.getUserId();
        CatalogUser catalogUser = catalogUserManager.load(userId);
        List<Category> categories = categoryManager.loadAll(catalogUser);
        log.info("{} 개의 카테고리를 가져왔습니다.", categories.size());
        return categories;
    }
}
