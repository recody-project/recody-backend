package com.recody.recodybackend.movie.data.people;

import com.recody.recodybackend.movie.data.movie.MovieEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "MovieDirector")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "movie_director")
@Getter
public class MovieDirectorEntity {
    
    @Id
    @GeneratedValue
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "director_contains_person_id"))
    private MoviePersonEntity person;
    
    @ManyToOne
    @JoinColumn(name = "movie_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "director_contains_movie_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MovieEntity movie;
    
    @Override
    public String toString() {
        return "{\"MovieDirectorEntity\":{" + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + "}}";
    }
}
