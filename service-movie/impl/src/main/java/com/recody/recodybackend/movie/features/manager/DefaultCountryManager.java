package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.data.productioncountry.CountryEntity;
import com.recody.recodybackend.movie.data.productioncountry.CountryManager;
import com.recody.recodybackend.movie.data.productioncountry.CountryRepository;
import com.recody.recodybackend.movie.data.productioncountry.ProductionCountryMapper;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.ProductionCountry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultCountryManager implements CountryManager {
    private final ProductionCountryMapper mapper;
    private final CountryRepository repository;
    
    @Override
    @Transactional
    public CountryEntity register(ProductionCountry productionCountry) {
        Optional<CountryEntity> optionalEntity;
        optionalEntity = repository.findById(productionCountry.getIso_3166_1());
        if (optionalEntity.isPresent()) {
            return optionalEntity.get();
        }
        CountryEntity entity = mapper.newCountryEntity(productionCountry);
        CountryEntity savedEntity = repository.save(entity);
        log.info("registered Country: {}", savedEntity);
        return savedEntity;
    }
}
