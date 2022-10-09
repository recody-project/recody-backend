package com.recody.recodybackend.movie.general;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Genre;
import lombok.Getter;
import lombok.Setter;

/*
* 영화의 장르는 여러개일 수 있다. */
@Getter
@Setter
public class MovieGenre implements Genre {
    private Integer genreId;
    private String genreName;
    private MovieSource source;
    
    public MovieGenre() { }
    
    public MovieGenre(Integer genreId) {
        this.genreId = genreId;
    }
    
    public MovieGenre(Integer genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }
    
    @Override
    public BasicCategory getCategory() {
        return BasicCategory.Movie;
    }
    
    @Override
    public String toString() {
        return "{\"MovieGenre\":{" + "\"genreId\":" + genreId + ", \"genreName\":" + ((genreName != null) ? ("\"" + genreName + "\"") : null) + "}}";
    }
}
