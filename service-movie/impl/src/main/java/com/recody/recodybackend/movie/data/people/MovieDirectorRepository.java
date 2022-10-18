package com.recody.recodybackend.movie.data.people;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieDirectorRepository extends JpaRepository<MovieDirectorEntity, UUID> {

}
