package com.recody.recodybackend.movie.data.people;

import com.recody.recodybackend.movie.data.movie.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MovieDirectorRepository extends JpaRepository<MovieDirectorEntity, UUID> {
    
    Optional<MovieDirectorEntity> findDistinctByMovieAndPerson(MovieEntity movie, MoviePersonEntity personEntity);
    
    
    
}
