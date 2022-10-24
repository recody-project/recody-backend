package com.recody.recodybackend.movie.data.movie;

import com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator;
import com.recody.recodybackend.movie.data.MovieBaseEntity;
import com.recody.recodybackend.movie.data.genre.MovieGenreEntity;
import com.recody.recodybackend.movie.data.people.MovieActorEntity;
import com.recody.recodybackend.movie.data.people.MovieDirectorEntity;
import com.recody.recodybackend.movie.data.productioncountry.ProductionCountryEntity;
import com.recody.recodybackend.movie.data.spokenlanguage.SpokenLanguageEntity;
import com.recody.recodybackend.movie.data.title.MovieTitleEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Movie")
@Table(name = "movie")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MovieEntity extends MovieBaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_seq")
    @GenericGenerator(name = "movie_seq",
                      strategy = "com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator",
                      parameters = {@Parameter(name = CustomSequenceIdGenerator.INCREMENT_PARAM, value = "50"),// high-low 최적화
                                    @Parameter(name = CustomSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "mov-"),
                                    @Parameter(name = CustomSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%d")})
    private String id;
    @Column(name = "tmdb_id", unique = true)
    private Integer tmdbId;
    
    
    private String originalLanguage;
    private String originalTitle;
    @Lob
    @Column
    private String overview;
    private Float popularity;
    private String posterPath;
    private String releaseDate;
    private Integer runtime;
    private Integer revenue;
    private String status;
    private Float voteAverage;
    private Integer voteCount;
    @OneToOne(mappedBy = "movie", cascade = CascadeType.ALL)
    private MovieTitleEntity title;
    
    @OneToMany(mappedBy = "movie")
    @Builder.Default
    private List<MovieGenreEntity> genres = new ArrayList<>();
    
    @OneToMany(mappedBy = "movie")
    @Builder.Default
    private List<ProductionCountryEntity> productionCountries = new ArrayList<>();
    
    @OneToMany(mappedBy = "movie")
    @Builder.Default
    private List<SpokenLanguageEntity> spokenLanguages = new ArrayList<>();
    
    @OneToMany(mappedBy = "movie")
    @Builder.Default
    private List<MovieActorEntity> actors = new ArrayList<>();
    
    @OneToMany(mappedBy = "movie")
    @Builder.Default
    private List<MovieDirectorEntity> directors = new ArrayList<>();
    
    @Override
    public String toString() {
        return "[{\"MovieEntity\":{" + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + ", \"tmdbId\":" + tmdbId + ", \"originalLanguage\":" + ((originalLanguage != null) ? ("\"" + originalLanguage + "\"") : null) + ", \"originalTitle\":" + ((originalTitle != null) ? ("\"" + originalTitle + "\"") : null) + ", \"overview\":" + ((overview != null) ? ("\"" + overview + "\"") : null) + ", \"popularity\":" + popularity + ", \"posterPath\":" + ((posterPath != null) ? ("\"" + posterPath + "\"") : null) + ", \"releaseDate\":" + ((releaseDate != null) ? ("\"" + releaseDate + "\"") : null) + ", \"runtime\":" + runtime + ", \"revenue\":" + revenue + ", \"status\":" + ((status != null) ? ("\"" + status + "\"") : null) + ", \"voteAverage\":" + voteAverage + ", \"voteCount\":" + voteCount + ", \"title\":" + title + "}}, " + super.toString() + "]";
    }
    
    public void setTitle(MovieTitleEntity title) {
        this.title = title;
        title.setMovie(this);
    }
}
