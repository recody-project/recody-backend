package com.recody.recodybackend.movie.features.recognize;

import com.recody.recodybackend.movie.data.productioncountry.ProductionCountryEntity;
import com.recody.recodybackend.movie.data.productioncountry.ProductionCountryMapper;
import com.recody.recodybackend.movie.data.productioncountry.ProductionCountryRepository;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.ProductionCountry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultProductionCountryManager implements ProductionCountryManager {
    
    private final ProductionCountryRepository repository;
    private final ProductionCountryMapper mapper;
    
    @Override
    @Transactional
    public String register(ProductionCountry productionCountry) {
        Optional<ProductionCountryEntity> optionalEntity;
        optionalEntity = repository.findById(productionCountry.getIso_3166_1());
        if (optionalEntity.isPresent()) {
            return optionalEntity.get().getId();
        }
        ProductionCountryEntity entity = mapper.map(productionCountry);
        ProductionCountryEntity savedEntity = repository.save(entity);
        String id = savedEntity.getId();
        log.info("recognized Production Country: id: {}", id);
        return id;
    }
}
