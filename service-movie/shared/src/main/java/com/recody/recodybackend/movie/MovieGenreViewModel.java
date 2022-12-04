package com.recody.recodybackend.movie;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class MovieGenreViewModel {
    private String genreId;
    private String genreName;
    
    public MovieGenreViewModel(String genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }
    
    @Override
    public String toString() {
        return "{\"MovieGenre\":{" + "\"genreId\":" + genreId + ", \"genreName\":" + ((genreName != null) ? ("\"" + genreName + "\"") : null) + "}}";
    }
}
