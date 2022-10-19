package com.recody.recodybackend.movie.data.people;

import com.recody.recodybackend.movie.data.movie.MovieEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "MovieActor")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "movie_actor")
@Getter
public class MovieActorEntity {
    
    @Id
    @GeneratedValue
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "actor_contains_movie_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MovieEntity movie;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "actor_contains_person_id"))
    private MoviePersonEntity person;
    
    @Column(name = "as_character")
    private String character;
    
    @Override
    public String toString() {
        return "{\"MovieActorEntity\":{" + "\"id\":" + ((id != null) ? ("\"" + id + "\"") : null) + ", \"character\":" + ((character != null) ? ("\"" + character + "\"") : null) + "}}";
    }
}
