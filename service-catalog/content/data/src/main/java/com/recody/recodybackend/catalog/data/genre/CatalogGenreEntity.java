package com.recody.recodybackend.catalog.data.genre;


import com.recody.recodybackend.catalog.data.category.CategoryEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Builder
@Getter
@Table( name = "catalog_genre" )
public class CatalogGenreEntity {
    
    @Id
    @GeneratedValue( generator = "genre_seq", strategy = GenerationType.SEQUENCE )
    @GenericGenerator( name = "genre_seq",
                       strategy = "com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator",
                       parameters = {
                               @Parameter( name = CustomSequenceIdGenerator.INCREMENT_PARAM, value = "50" ),
                               @Parameter( name = CustomSequenceIdGenerator.INITIAL_PARAM, value = "100" ),
                               @Parameter( name = CustomSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "g-" ),
                               @Parameter( name = CustomSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%d" )} )
    private String id;
    
    @Column( name = "name", nullable = false )
    private String name;
    
    @Column( name = "icon_url" )
    private String iconUrl;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "category_id" )
    private CategoryEntity category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "user_id")
    private CatalogUserEntity user;
    
    @Override
    public String toString() {
        return "{\"CatalogGenreEntity\":{"
               + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null)
               + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null)
               + "}}";
    }
}
