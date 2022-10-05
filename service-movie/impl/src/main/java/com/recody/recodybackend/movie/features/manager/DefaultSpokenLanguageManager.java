package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.data.spokenlanguage.LanguageEntity;
import com.recody.recodybackend.movie.data.spokenlanguage.SpokenLanguageMapper;
import com.recody.recodybackend.movie.data.spokenlanguage.SpokenLanguageRepository;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.SpokenLanguage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultSpokenLanguageManager implements SpokenLanguageManager {
    
    private final SpokenLanguageRepository repository;
    private final SpokenLanguageMapper mapper;

    @Override
    @Transactional
    public String register(SpokenLanguage spokenLanguage) {
        String iso_639_1 = spokenLanguage.getIso_639_1();
        Optional<LanguageEntity> optionalLanguageEntity = repository.findById(iso_639_1);
        if (optionalLanguageEntity.isPresent()){
            return optionalLanguageEntity.get().getId();
        }
        LanguageEntity languageEntity = mapper.toEntity(spokenLanguage);
        LanguageEntity savedEntity = repository.save(languageEntity);
        String id = savedEntity.getId();
        log.info("SpokenLanguage Recognized: id: {}", id);
        return id;
    }
}
