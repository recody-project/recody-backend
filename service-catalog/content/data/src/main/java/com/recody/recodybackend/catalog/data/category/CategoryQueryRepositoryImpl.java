package com.recody.recodybackend.catalog.data.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.recody.recodybackend.catalog.data.category.QPersonalizedCategoryEntity.personalizedCategoryEntity;

@Repository
@RequiredArgsConstructor
@Slf4j
class CategoryQueryRepositoryImpl implements CategoryQueryRepository{
    
    private final JPAQueryFactory jpaQueryFactory;
    
    // deleted_at = null 조건을 주는 것을 잊지 마세요.
    @Override
    public Optional<CategoryEntity> findByUserIdAndContentFromJoinedPersonalizedCategory(Long userId, CatalogContentEntity content) {
        return Optional.ofNullable(
                jpaQueryFactory.select( personalizedCategoryEntity.category )
                               .from( personalizedCategoryEntity )
                               .where( personalizedCategoryEntity.user.id.eq( userId ),
                                       personalizedCategoryEntity.content.eq( content ),
                                       personalizedCategoryEntity.category.deletedAt.isNull() )
                               .fetchOne()
                                  );
    }
}
