package com.recody.recodybackend.catalog.data.genre;


import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Builder
@Getter
@Table(name = "catalog_genre")
public class CatalogGenreEntity {
    
    @Id
    private String id;
    
    private String name;
    
    @ManyToOne
    @JoinColumn( name = "category_id" )
    private CategoryEntity category;
    
    
}
