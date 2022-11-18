package com.recody.recodybackend.movie;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class MovieGenres {
    
    private List<MovieGenre> values;
    
    public MovieGenres(List<MovieGenre> values) {
        this.values = values;
    }
    
    
    
    @Override
    public String toString() {
        return "{\"MovieGenres\":{"
               + "\"values\":" + values
               + "}}";
    }
}
