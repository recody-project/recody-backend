package com.recody.recodybackend.movie.general;

public class MovieGenre {
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
}
