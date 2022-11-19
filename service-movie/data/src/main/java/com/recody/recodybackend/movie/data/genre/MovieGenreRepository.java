package com.recody.recodybackend.movie.data.genre;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieGenreRepository extends JpaRepository<MovieGenreEntity, String> {
}
