package com.recody.recodybackend.catalog.features.category.add;

import com.recody.recodybackend.catalog.features.CatalogUser;
import com.recody.recodybackend.catalog.features.CustomCategory;
import com.recody.recodybackend.catalog.features.category.CategoryIconUrl;
import com.recody.recodybackend.catalog.features.category.CategoryName;
import com.recody.recodybackend.catalog.features.manager.CatalogUserManager;
import com.recody.recodybackend.catalog.features.manager.CategoryManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultAddCategoryHandler implements AddCategoryHandler {
    
    private final CatalogUserManager userManager;
    private final CategoryManager categoryManager;
    
    @Override
    public CustomCategory handle(AddCategory command) {
        log.debug("handling command: {}", command);
        Long userId = command.getUserId();
        
        CatalogUser catalogUser = userManager.load(userId);
        
        CustomCategory registeredCategory
                = categoryManager.register(
                CategoryName.of(command.getName()),
                CategoryIconUrl.of(command.getIconUrl()),
                catalogUser);
        
        log.info("커스텀 카테고리를 추가하였습니다.: {}", registeredCategory);
        return registeredCategory;
        
    }
}
