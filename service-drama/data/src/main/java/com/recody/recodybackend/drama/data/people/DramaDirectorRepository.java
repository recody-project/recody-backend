package com.recody.recodybackend.drama.data.people;

import com.recody.recodybackend.drama.data.drama.DramaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DramaDirectorRepository extends JpaRepository<DramaDirectorEntity, Long> {
    
    Optional<DramaDirectorEntity> findByDramaAndPerson(DramaEntity drama, DramaPersonEntity person);
    
    
    
}
