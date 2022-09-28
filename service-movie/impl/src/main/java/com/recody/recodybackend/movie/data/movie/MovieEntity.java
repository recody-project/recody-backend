package com.recody.recodybackend.movie.data.movie;

import com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "movie")
@Getter
@Setter
public class MovieEntity {
    
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
    private String overview;
    private Float popularity;
    private String posterPath;
    @OneToMany(mappedBy = "movie",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Getter(AccessLevel.PROTECTED)
    private List<MovieProductionCountryRelation> productionCountries;
    private String releaseDate;
    private Integer runtime;
    private Integer revenue;
    @OneToMany(mappedBy = "movie", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MovieGenreRelation> genres;
    @OneToMany(mappedBy = "movie", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MovieLanguageRelation> spokenLanguages;
    private String status;
    private String title;
    private Float voteAverage;
    private Integer voteCount;
    
    @Override
    public String toString() {
        return "{\"MovieEntity\":{" + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + ", \"tmdbId\":" + tmdbId + ", \"originalLanguage\":" + ((originalLanguage != null) ? ("\"" + originalLanguage + "\"") : null) + ", \"originalTitle\":" + ((originalTitle != null) ? ("\"" + originalTitle + "\"") : null) + ", \"overview\":" + ((overview != null) ? ("\"" + overview + "\"") : null) + ", \"popularity\":" + popularity + ", \"posterPath\":" + ((posterPath != null) ? ("\"" + posterPath + "\"") : null) + ", \"productionCountries\":" + productionCountries + ", \"releaseDate\":" + ((releaseDate != null) ? ("\"" + releaseDate + "\"") : null) + ", \"runtime\":" + runtime + ", \"revenue\":" + revenue + ", \"genres\":" + genres + ", \"spokenLanguages\":" + spokenLanguages + ", \"status\":" + ((status != null) ? ("\"" + status + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + ", \"voteAverage\":" + voteAverage + ", \"voteCount\":" + voteCount + "}}";
    }
}
