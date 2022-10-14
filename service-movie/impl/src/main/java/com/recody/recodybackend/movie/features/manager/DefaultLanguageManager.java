package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.data.spokenlanguage.*;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.SpokenLanguage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultLanguageManager implements LanguageManager {
    
    private final LanguageRepository repository;
    private final SpokenLanguageMapper mapper;

    @Override
    @Transactional
    public LanguageEntity register(SpokenLanguage spokenLanguage) {
        String iso_639_1 = spokenLanguage.getIso_639_1();
        Optional<LanguageEntity> optionalLanguageEntity = repository.findById(iso_639_1);
        if (optionalLanguageEntity.isPresent()){
            return optionalLanguageEntity.get();
        }
        LanguageEntity languageEntity = mapper.toEntity(spokenLanguage);
        return repository.save(languageEntity);
    }
}
