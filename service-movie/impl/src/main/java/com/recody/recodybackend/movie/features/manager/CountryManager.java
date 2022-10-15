package com.recody.recodybackend.movie.features.manager;

import com.recody.recodybackend.movie.data.productioncountry.CountryEntity;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.ProductionCountry;

public interface CountryManager {
    
    CountryEntity register(ProductionCountry productionCountry);
    
}
