package com.recody.recodybackend.movie.data.search;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieSearchingKeywordCountRepository extends JpaRepository<MovieSearchingKeywordCountEntity, Long> {
    
    Optional<MovieSearchingKeywordCountEntity> findByKeyword(String keyword);
    
}
