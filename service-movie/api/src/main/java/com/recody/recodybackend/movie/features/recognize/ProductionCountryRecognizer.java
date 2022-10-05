package com.recody.recodybackend.movie.features.recognize;

import com.recody.recodybackend.movie.features.getmoviedetail.dto.ProductionCountry;

public interface ProductionCountryRecognizer {
    
    String recognize(ProductionCountry productionCountry);
    
}
