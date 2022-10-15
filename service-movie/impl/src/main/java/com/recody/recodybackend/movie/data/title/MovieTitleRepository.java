package com.recody.recodybackend.movie.data.title;

import com.recody.recodybackend.movie.data.movie.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieTitleRepository extends JpaRepository<MovieTitleEntity, Long> {
    
    Optional<MovieTitleEntity> findByMovie(MovieEntity movie);
}
