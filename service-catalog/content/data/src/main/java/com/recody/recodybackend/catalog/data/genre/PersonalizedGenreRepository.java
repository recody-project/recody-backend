package com.recody.recodybackend.catalog.data.genre;

import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PersonalizedGenreRepository extends JpaRepository<PersonalizedGenreEntity, UUID> {
    

    List<PersonalizedGenreEntity> findAllByContentAndUser(CatalogContentEntity content, CatalogUserEntity user);
    
    void deleteAllByContentAndUser(CatalogContentEntity content, CatalogUserEntity user);

}
