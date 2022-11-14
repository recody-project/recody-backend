package com.recody.recodybackend.catalog.data.category;

import com.recody.recodybackend.catalog.data.LookupId;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import lombok.*;

import javax.persistence.*;

/**
 * 유저가 작품의 상세정보를 조회할 때 카테고리 커스텀 정보는 항상 저장되어야 한다.
 * @author motive
 */
@Entity
@Table(name = "catalog_category_personalization")
@IdClass( LookupId.class )
@Getter
@Builder
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@AllArgsConstructor
@Setter
public class PersonalizedCategoryEntity {
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
                foreignKey = @ForeignKey(name = "personalized_category_contains_user_id"))
    private CatalogUserEntity user;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id",
                foreignKey = @ForeignKey(name = "personalized_category_contains_content_id"))
    private CatalogContentEntity content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",
                foreignKey = @ForeignKey(name = "personalized_category_contains_category_id"))
    private CategoryEntity category;
    
    
    
}
