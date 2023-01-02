package com.recody.recodybackend.drama.features.fetchdramadetail;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class TMDBDramaDetail {
    
    private Integer id;
    // 제목
    private String name;
    @JsonAlias( "original_name" )
    private String originalName;
    private String overview;
    
    // 개봉연도
    @JsonAlias( "first_air_date" )
    private String firstAirDate;
    // 감독
    @JsonAlias( "created_by" )
    private List<TMDBDramaCreatedBy> createdBy;
    
    @JsonAlias( "poster_path" )
    private String posterPath;
    
    // 방송사
    private List<TMDBDramaNetwork> networks;
    
    // 총 회차
    @JsonAlias( "number_of_episodes" )
    private Integer numberOfEpisodes;
    
    @Override
    public String toString() {
        return "{\"TMDBDramaDetail\":{"
               + "\"id\":" + id
               + ", \"name\":" + ((name != null) ? ("\"" + name + "\"") : null)
               + ", \"originalName\":" + ((originalName != null) ? ("\"" + originalName + "\"") : null)
               + ", \"overview\":" + ((overview != null) ? ("\"" + overview + "\"") : null)
               + ", \"firstAirDate\":" + ((firstAirDate != null) ? ("\"" + firstAirDate + "\"") : null)
               + ", \"createdBy\":" + createdBy
               + ", \"posterPath\":" + ((posterPath != null) ? ("\"" + posterPath + "\"") : null)
               + ", \"networks\":" + networks
               + ", \"numberOfEpisodes\":" + numberOfEpisodes
               + "}}";
    }
}
