package com.recody.recodybackend.catalog.features.record.resolvecategory;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.exceptions.UnsupportedCategoryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultCategoryResolver implements CategoryResolver{
    
    @Override
    public Category resolve(String id) {
        log.debug("resolving categoryId: {}", id);
        if (id == null) return null;
        if (!StringUtils.hasText(id)) {
            log.debug("all category");
            return null;
        }
        if (BasicCategory.isBasic(id)) {
            BasicCategory category = BasicCategory.idOf(id);
            log.debug("resolved category: {}", category);
            return category;
        }
        log.warn("id: {} is not supported", id);
        throw new UnsupportedCategoryException();
    }
}
