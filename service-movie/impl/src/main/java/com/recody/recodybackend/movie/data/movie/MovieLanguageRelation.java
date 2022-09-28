package com.recody.recodybackend.movie.data.movie;

import com.recody.recodybackend.movie.data.LanguageEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class MovieLanguageRelation {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "language_id")
    private LanguageEntity language;
    
    @Override
    public String toString() {
        return "{\"MovieLanguageRelation\":{" + "\"id\":" + id + ", \"movie\":" + movie + ", \"language\":" + language + "}}";
    }
}
