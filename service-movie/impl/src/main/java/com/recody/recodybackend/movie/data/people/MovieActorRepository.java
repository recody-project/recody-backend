package com.recody.recodybackend.movie.data.people;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieActorRepository extends JpaRepository<MovieActorEntity, UUID> {

}
