package com.recody.recodybackend.movie.search;

import lombok.Getter;

public class MovieSearchKeyword {
    
    @Getter
    private final String value;
    
    private MovieSearchKeyword(String value) {
        this.value = value;
    }
    
    public static MovieSearchKeyword of(String keyword) {
        return new MovieSearchKeyword( keyword );
    }
    
    @Override
    public String toString() {
        return "{\"MovieSearchKeyword\":{"
               + "\"value\":" + ((value != null) ? ("\"" + value + "\"") : null)
               + "}}";
    }
}
