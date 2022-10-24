package com.recody.recodybackend.movie.data.productioncountry;

import com.recody.recodybackend.common.data.AsyncEntityRegistrar;
import com.recody.recodybackend.movie.features.getmoviedetail.dto.ProductionCountry;

public interface CountryManager extends AsyncEntityRegistrar<CountryEntity, ProductionCountry> {
    
    @Override
    CountryEntity register(ProductionCountry productionCountry);
    
}
