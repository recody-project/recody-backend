package com.recody.recodybackend.movie.features.searchmoviesfromtmdb;

import lombok.*;

import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchMoviesFromTMDB {
    
    private String movieName;
    private String language;
    
    @Min( value = 1 )
    private Integer page;
}
