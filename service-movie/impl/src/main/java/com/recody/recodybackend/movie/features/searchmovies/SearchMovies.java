package com.recody.recodybackend.movie.features.searchmovies;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchMovies {
    private String movieName;
    private String language;
    
    private Integer size;
    
    private Integer page;
    
    @Override
    public String toString() {
        return "{\"SearchMovies\":{"
               + "\"movieName\":" + ((movieName != null) ? ("\"" + movieName + "\"") : null)
               + ", \"language\":" + ((language != null) ? ("\"" + language + "\"") : null)
               + "}}";
    }
}
