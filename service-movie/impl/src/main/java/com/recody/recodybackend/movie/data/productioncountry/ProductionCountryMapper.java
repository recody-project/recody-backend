package com.recody.recodybackend.movie.data.productioncountry;

import com.recody.recodybackend.movie.features.getmoviedetail.ProductionCountry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductionCountryMapper {
    
    @Mapping(target = "id", source = "iso_3166_1")
    ProductionCountryEntity map(ProductionCountry productionCountry);
    
    @Mapping(target = "iso_3166_1", source = "id")
    ProductionCountry map(ProductionCountryEntity productionCountry);
}
