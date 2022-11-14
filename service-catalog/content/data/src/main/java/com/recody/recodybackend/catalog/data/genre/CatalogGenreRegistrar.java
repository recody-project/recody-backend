package com.recody.recodybackend.catalog.data.genre;

import com.recody.recodybackend.catalog.data.content.CatalogContentRepository;
import com.recody.recodybackend.catalog.data.personalcontent.PersonalizedContentEntity;
import com.recody.recodybackend.common.data.AsyncLinkingEntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class CatalogGenreRegistrar implements AsyncLinkingEntityManager<PersonalizedGenreEntity, PersonalizedContentEntity, CatalogGenreEntity> {
    
    private final CatalogContentRepository contentRepository;
    private final CatalogGenreRepository genreRepository;
    private final PersonalizedGenreRepository contentGenreRepository;
    
    @Override
    public PersonalizedGenreEntity save(PersonalizedContentEntity content, CatalogGenreEntity catalogGenreEntity) {
        List<PersonalizedGenreEntity> contentGenres = contentGenreRepository.findAllByContent( content );
        return null;
    }
}
