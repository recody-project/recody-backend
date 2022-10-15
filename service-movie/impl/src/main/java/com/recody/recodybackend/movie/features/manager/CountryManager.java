package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.data.productioncountry.CountryEntity;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.ProductionCountry;

import java.util.concurrent.CompletableFuture;

public interface CountryManager {
    
    CountryEntity register(ProductionCountry productionCountry);
    
    CompletableFuture<CountryEntity> registerAsync(ProductionCountry productionCountry);
    
}
