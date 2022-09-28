package com.recody.recodybackend.movie.data.movie;

import com.recody.recodybackend.movie.data.MovieGenreEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
public class MovieGenreRelation {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id")
    private MovieGenreEntity genre;
    
    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }
    
    public void setGenre(MovieGenreEntity genre) {
        this.genre = genre;
    }
    
    @Override
    public String toString() {
        return "{\"MovieGenreRelation\":{" + "\"id\":" + id;
    }
}
