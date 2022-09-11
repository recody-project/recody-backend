package com.recody.recodybackend.movie.data;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MovieGenreEntity {
    @Id @GeneratedValue
    private Integer genreId;
    
    private Integer tmdbGenreId;
    private String tmdbGenreName;
}
