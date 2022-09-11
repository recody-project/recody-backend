package com.recody.recodybackend.movie.data;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "movie_genre")
public class MovieGenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer genreId;
    
    private Integer tmdbGenreId;
    private String tmdbGenreName;
}
