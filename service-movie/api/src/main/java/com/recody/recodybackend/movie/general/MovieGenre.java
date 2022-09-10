package com.recody.recodybackend.movie.general;

import com.recody.recodybackend.common.contents.Genre;

/*
* 영화의 장르는 여러개일 수 있다. */
public class MovieGenre implements Genre {
    private final Integer genreId;
    private String genreName;
    
    public MovieGenre(Integer genreId) {
        this.genreId = genreId;
    }
    
    public MovieGenre(Integer genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }
    
    public Integer getGenreId() {
        return genreId;
    }
    
    public String getGenreName() {
        return genreName;
    }
    
    @Override
    public String toString() {
        return "{\"MovieGenre\":{" + "\"genreId\":" + genreId + ", \"genreName\":" + ((genreName != null) ? ("\"" + genreName + "\"") : null) + "}}";
    }
}
