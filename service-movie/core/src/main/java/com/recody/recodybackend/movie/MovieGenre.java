package com.recody.recodybackend.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.contents.Genre;
import lombok.*;

/*
* 영화의 장르는 여러개일 수 있다. */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieGenre implements Genre {
    
    /**
     * 이 id 는 레코디에서 부여한 고유 장르 id 이어야 한다.
     */
    private String genreId;
    private String genreName;
    private MovieSource source;
    
    
    
    public MovieGenre(String genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }
    
    @Override
    @JsonIgnore
    public Category getCategory() {
        return BasicCategory.Movie;
    }
    
    @Override
    public String toString() {
        return "{\"MovieGenre\":{" + "\"genreId\":" + genreId + ", \"genreName\":" + ((genreName != null) ? ("\"" + genreName + "\"") : null) + "}}";
    }
}
