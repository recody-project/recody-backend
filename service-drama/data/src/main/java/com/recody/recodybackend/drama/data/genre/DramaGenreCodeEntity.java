package com.recody.recodybackend.drama.data.genre;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "drama_genre_code" )
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Getter
@Builder
public class DramaGenreCodeEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
}
