package com.recody.recodybackend.catalog.data.genre;

import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogGenreRepository extends JpaRepository<CatalogGenreEntity, String> {
    
    Optional<CatalogGenreEntity> findByUserAndName(CatalogUserEntity user, String genreName);
    Optional<CatalogGenreEntity> findByIdAndUser(String id, CatalogUserEntity user);

}
