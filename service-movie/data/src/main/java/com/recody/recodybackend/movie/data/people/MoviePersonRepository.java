package com.recody.recodybackend.movie.data.people;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MoviePersonRepository extends JpaRepository<MoviePersonEntity, Long> {
    

    Optional<MoviePersonEntity> findByTmdbId(Integer tmdbId);
    @Query("select p.id from MoviePerson p where p.tmdbId = :tmdbId")
    Optional<Long> findIdByTmdbId(@Param("tmdbId") Integer tmdbId);
    
    Optional<MoviePersonEntity> findByName_Id(Long personNameId);
}
