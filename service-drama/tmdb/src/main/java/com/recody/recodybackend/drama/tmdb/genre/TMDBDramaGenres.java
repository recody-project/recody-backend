package com.recody.recodybackend.drama.tmdb.genre;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TMDBDramaGenres {
    
    @JsonAlias("genres")
    List<TMDBDramaGenre> values;
    
    public TMDBDramaGenres(List<TMDBDramaGenre> values) {
        this.values = values;
    }
    
    public static TMDBDramaGenres of(List<TMDBDramaGenre> values) {
        return new TMDBDramaGenres( values );
    }
    
    @Override
    public String toString() {
        return "{\"TMDBDramaGenres\":{"
               + "\"values\":" + values
               + "}}";
    }
}
