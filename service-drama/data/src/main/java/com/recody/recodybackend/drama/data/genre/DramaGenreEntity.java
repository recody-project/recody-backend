package com.recody.recodybackend.drama.data.genre;

import com.recody.recodybackend.drama.data.drama.DramaEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table( name = "drama_genre",
        uniqueConstraints = @UniqueConstraint( name = "drama_id_and_drama_genre_id_should_be_unique",
                                               columnNames = {"drama_id", "drama_genre_id"}))
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Getter
@Builder
public class DramaGenreEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @JoinColumn( name = "drama_id",
                 nullable = false,
                 foreignKey = @ForeignKey( name = "genre_contains_drama_id" ) )
    @OneToOne( fetch = FetchType.LAZY )
    private DramaEntity drama;
    
    @JoinColumn( name = "drama_genre_id",
                 nullable = false,
                 foreignKey = @ForeignKey( name = "genre_contains_genre_code_id" ) )
    @OneToOne( fetch = FetchType.LAZY )
    private DramaGenreCodeEntity genreCode;
    
    @Override
    public String toString() {
        return "{\"DramaGenreEntity\":{"
               + "\"id\":" + id
               + ", \"drama\":" + drama
               + ", \"genreCode\":" + genreCode
               + "}}";
    }
}
