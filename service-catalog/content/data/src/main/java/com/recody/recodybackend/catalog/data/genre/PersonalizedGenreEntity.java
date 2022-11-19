package com.recody.recodybackend.catalog.data.genre;

import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Builder
@Getter
@Table(name = "catalog_genre_personalization",
       uniqueConstraints = {
        @UniqueConstraint( name = "genre_for_each_user_and_content", columnNames = {"user_id","content_id","genre_id"})
})
public class PersonalizedGenreEntity {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;
    
    @ManyToOne
    @JoinColumn( name = "user_id", foreignKey = @ForeignKey( name = "content_genre_contains_user_id" ), nullable = false )
    private CatalogUserEntity user;
    
    @ManyToOne
    @JoinColumn( name = "content_id", foreignKey = @ForeignKey( name = "content_genre_contains_content_id" ), nullable = false )
    private CatalogContentEntity content;
    
    @ManyToOne
    @JoinColumn( name = "genre_id", foreignKey = @ForeignKey( name = "content_genre_contains_genre_id" ), nullable = false )
    private CatalogGenreEntity genre;
    
}
