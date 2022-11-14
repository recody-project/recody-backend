package com.recody.recodybackend.catalog.data.genre;

import com.recody.recodybackend.catalog.data.personalcontent.PersonalizedContentEntity;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Builder
@Getter
@Table(name = "catalog_genre_personalization")
public class PersonalizedGenreEntity {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;
    
    @ManyToOne
    @JoinColumn( name = "genre_id", nullable = false )
    private CatalogGenreEntity genre;
    
    @ManyToOne
    @JoinColumns( value = {
            @JoinColumn( name = "content_id", foreignKey = @ForeignKey( name = "content_genre_contains_content_id" ), nullable = false ),
            @JoinColumn( name = "user_id", foreignKey = @ForeignKey( name = "content_genre_contains_user_id" ), nullable = false ),
    } )
    private PersonalizedContentEntity content;
    
    
}
