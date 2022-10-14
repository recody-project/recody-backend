package com.recody.recodybackend.movie.data.productioncountry;

import com.recody.recodybackend.movie.features.getmoviedetail.dto.ProductionCountry;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class ProductionCountryMapper {



//    @Mapping(target = "movie", source = "")
//    @Mapping(target = "country", source = "")
//    @Mapping(target = "id", source = "iso_3166_1")
//    public abstract ProductionCountryEntity map(ProductionCountry productionCountry);
//
    @Mapping(target = "iso_3166_1", source = "productionCountry.country.id")
    @Mapping(target = "name", source = "productionCountry.country.name")
    public abstract ProductionCountry map(ProductionCountryEntity productionCountry);
    
    @Mapping(target = "id", source = "iso_3166_1")
    public abstract CountryEntity mapCountry(ProductionCountry productionCountry);
}
