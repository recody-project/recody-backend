package com.recody.recodybackend.catalog.data.category;

import com.recody.recodybackend.catalog.data.LookupId;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import lombok.*;

import javax.persistence.*;

/**
 * 유저가 작품의 상세정보를 조회할 때 카테고리 커스텀 정보는 항상 저장되어야 한다.
 * @author motive
 */
@Entity
@Table(name = "category_personalization")
@IdClass( LookupId.class )
@Getter
@Builder
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@AllArgsConstructor
@Setter
public class PersonalizedCategoryEntity {
    
    @Id
    private Long userId;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private CatalogContentEntity content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity category;
    
    
    
}
