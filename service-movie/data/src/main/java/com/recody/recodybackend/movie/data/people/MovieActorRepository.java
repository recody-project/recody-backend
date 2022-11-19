package com.recody.recodybackend.movie.data.people;

import com.recody.recodybackend.movie.data.movie.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MovieActorRepository extends JpaRepository<MovieActorEntity, UUID> {
    
    Optional<MovieActorEntity> findDistinctByMovieAndPerson(MovieEntity movieEntity, MoviePersonEntity personEntity);
}
