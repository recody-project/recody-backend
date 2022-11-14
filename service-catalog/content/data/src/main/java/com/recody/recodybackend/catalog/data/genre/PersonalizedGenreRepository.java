package com.recody.recodybackend.catalog.data.genre;

import com.recody.recodybackend.catalog.data.personalcontent.PersonalizedContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PersonalizedGenreRepository extends JpaRepository<PersonalizedGenreEntity, UUID> {
    
    List<PersonalizedGenreEntity> findAllByContent(PersonalizedContentEntity content);

}
