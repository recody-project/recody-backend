package com.recody.recodybackend.movie;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Category;
import com.recody.recodybackend.common.contents.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class MovieGenreViewModel implements Genre {
    private String genreId;
    private String genreName;
    
    public MovieGenreViewModel(String genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }
    
    public MovieGenreViewModel(MovieGenre movieGenre) {
        this.genreId = movieGenre.getGenreId();
        this.genreName = movieGenre.getGenreName();
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
