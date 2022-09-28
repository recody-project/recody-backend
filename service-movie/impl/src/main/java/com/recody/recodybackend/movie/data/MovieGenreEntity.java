package com.recody.recodybackend.movie.data;

import com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "movie_genre")
public class MovieGenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_genre_seq")
    @GenericGenerator(
            name = "movie_genre_seq",
            strategy = "com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = CustomSequenceIdGenerator.INCREMENT_PARAM, value = "50"),// high-low 최적화
                    @org.hibernate.annotations.Parameter(name = CustomSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "mg-"),
                    @org.hibernate.annotations.Parameter(name = CustomSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%d") })
    private String genreId;
    
    private Integer tmdbGenreId;
    private String tmdbGenreName;
    
    @Override
    public String toString() {
        return "{\"MovieGenreEntity\":{" + "\"genreId\":" + ((genreId != null) ? ("\"" + genreId + "\"") : null) + ", \"tmdbGenreId\":" + tmdbGenreId + ", \"tmdbGenreName\":" + ((tmdbGenreName != null) ? ("\"" + tmdbGenreName + "\"") : null) + "}}";
    }
}
