package com.recody.recodybackend.movie;

import com.recody.recodybackend.common.contents.BasicContentDetail;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 영화의 상세정보.
 * <ul>
 *     <li> 유저에게 보여질 영화의 상세정보입니다.</li>
 *     <li> 영화의 기본적인 정보, 장르, 카테고리, 배우, 감독 정보가 포함됩니다. </li>
 * </ul>
 * @author motive
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieDetail extends Movie implements BasicContentDetail {
    
//    private Integer tmdbId;
    private MovieSource source;
    private String originalTitle;
    private String overview;
    private String releaseDate;
    private Integer runtime;
    
    
    /* Details */
    
//    private String originalLanguage;
//    private Float popularity;
//    private List<ProductionCountry> productionCountries;
//    private Long revenue;
//    private List<SpokenLanguage> spokenLanguages;
//    private String status;
//    private Float voteAverage;
//    private Integer voteCount;
    private List<MovieGenre> genres;
    
    private List<Actor> actors;
    private List<Director> directors;
    
    @Override
    public String toString() {
        return "[{\"MovieDetail\":{" + "\"source\":" + ((source != null) ? ("\"" + source + "\"") : null) + ", \"originalTitle\":" + ((originalTitle != null) ? ("\"" + originalTitle + "\"") : null) + ", \"overview\":" + ((overview != null) ? ("\"" + overview + "\"") : null) + ", \"releaseDate\":" + ((releaseDate != null) ? ("\"" + releaseDate + "\"") : null) + ", \"runtime\":" + runtime + ", \"genres\":" + genres + ", \"actors\":" + actors + ", \"directors\":" + directors + "}}, " + super.toString() + "]";
    }
}
