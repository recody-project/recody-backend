package com.recody.recodybackend.catalog.data.personalcontent;

import com.recody.recodybackend.catalog.data.LookupId;
import com.recody.recodybackend.catalog.data.content.CatalogContentEntity;
import com.recody.recodybackend.catalog.data.user.CatalogUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalizedContentRepository extends JpaRepository<PersonalizedContentEntity, LookupId> {
    
    Optional<PersonalizedContentEntity> findByContentAndUser(CatalogContentEntity content, CatalogUserEntity user);
}
