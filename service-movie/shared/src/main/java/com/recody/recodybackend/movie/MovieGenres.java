package com.recody.recodybackend.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class MovieGenres {
    
    @JsonProperty("movieGenres")
    private List<MovieGenreViewModel> values;
    
    public MovieGenres(List<MovieGenreViewModel> values) {
        this.values = values;
    }
    
    
    
    @Override
    public String toString() {
        return "{\"MovieGenres\":{"
               + "\"values\":" + values
               + "}}";
    }
}
