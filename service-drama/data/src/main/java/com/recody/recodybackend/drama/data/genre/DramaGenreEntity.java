package com.recody.recodybackend.drama.data.genre;

import com.recody.recodybackend.drama.data.drama.DramaEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table( name = "drama_genre" )
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
                 unique = true,
                 foreignKey = @ForeignKey( name = "genre_contains_drama_id" ) )
    @OneToOne( fetch = FetchType.LAZY )
    @OnDelete( action = OnDeleteAction.CASCADE )
    private DramaEntity drama;
    
}
