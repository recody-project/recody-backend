package com.recody.recodybackend.movie.features.searchmovies;

import com.recody.recodybackend.common.contents.GenreIds;
import lombok.*;

import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchMovies {
    private String movieName;
    private String language;
    
    @Builder.Default
    private Integer size = 10;
    
    @Min( value = 1 )
    private Integer page;
    
    private GenreIds genreIds;
    
    @Override
    public String toString() {
        return "{\"SearchMovies\":{"
               + "\"movieName\":" + ((movieName != null) ? ("\"" + movieName + "\"") : null)
               + ", \"language\":" + ((language != null) ? ("\"" + language + "\"") : null)
               + "}}";
    }
}
