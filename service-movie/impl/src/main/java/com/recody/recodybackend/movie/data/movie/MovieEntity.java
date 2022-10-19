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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    @GenericGenerator(
            name = "movie_seq",
            strategy = "com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator",
            parameters = {
                    @Parameter(name = CustomSequenceIdGenerator.INCREMENT_PARAM, value = "50"),// high-low 최적화
                    @Parameter(name = CustomSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "mov-"),
                    @Parameter(name = CustomSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%d") })
    private String id;
    private Integer tmdbId;
    
    
    private String originalLanguage;
    private String originalTitle;
    @Lob
    @Column
    private String overview;
    private Float popularity;
    private String posterPath;
    @OneToMany(mappedBy = "movie")
    private List<ProductionCountryEntity> productionCountries = new ArrayList<>();
    private String releaseDate;
    private Integer runtime;
    private Integer revenue;
    @OneToMany(mappedBy = "movie")
    private List<MovieGenreEntity> genres = new ArrayList<>();
    @OneToMany(mappedBy = "movie")
    private List<SpokenLanguageEntity> spokenLanguages = new ArrayList<>();
    private String status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "title_id", nullable = false, foreignKey = @ForeignKey(name = "movie_contains_movie_title_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MovieTitleEntity title;
    private Float voteAverage;
    private Integer voteCount;
    
    @OneToMany(mappedBy = "movie")
    private List<MovieActorEntity> actors = new ArrayList<>();
    
    @OneToMany(mappedBy = "movie")
    private List<MovieDirectorEntity> directors = new ArrayList<>();
    
    @Override
    public String toString() {
        return "[{\"MovieEntity\":{" + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + ", \"tmdbId\":" + tmdbId + ", \"originalLanguage\":" + ((originalLanguage != null) ? ("\"" + originalLanguage + "\"") : null) + ", \"originalTitle\":" + ((originalTitle != null) ? ("\"" + originalTitle + "\"") : null) + ", \"overview\":" + ((overview != null) ? ("\"" + overview + "\"") : null) + ", \"popularity\":" + popularity + ", \"posterPath\":" + ((posterPath != null) ? ("\"" + posterPath + "\"") : null) + ", \"productionCountries\":" + productionCountries + ", \"releaseDate\":" + ((releaseDate != null) ? ("\"" + releaseDate + "\"") : null) + ", \"runtime\":" + runtime + ", \"revenue\":" + revenue + ", \"genres\":" + genres + ", \"spokenLanguages\":" + spokenLanguages + ", \"status\":" + ((status != null) ? ("\"" + status + "\"") : null) + ", \"title\":" + title + ", \"voteAverage\":" + voteAverage + ", \"voteCount\":" + voteCount + ", \"actors\":" + actors + ", \"directors\":" + directors + "}}, " + super.toString() + "]";
    }
}
