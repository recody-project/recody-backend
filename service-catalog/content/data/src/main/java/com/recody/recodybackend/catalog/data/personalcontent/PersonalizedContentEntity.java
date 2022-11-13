package com.recody.recodybackend.catalog.data.personalcontent;

import com.recody.recodybackend.catalog.data.LookupId;
import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.genre.PersonalizedGenreEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Getter
@Builder
@IdClass( LookupId.class )
@Table( name = "catalog_personalized_content" )
public class PersonalizedContentEntity {
    
    @Id
    @ManyToOne
    @JoinColumn( name = "user_id" )
    private CatalogUserEntity user;
    
    @Id
    @ManyToOne
    @JoinColumn( name = "content_id" )
    private CatalogContentEntity content;
    
    @OneToMany( mappedBy = "content" )
    private List<PersonalizedGenreEntity> genres;
    
    @ManyToOne
    private CategoryEntity category;
    
}
