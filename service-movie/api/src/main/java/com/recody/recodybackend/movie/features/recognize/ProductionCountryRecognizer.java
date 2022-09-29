package com.recody.recodybackend.movie.features.recognize;

import com.recody.recodybackend.movie.features.getmoviedetail.ProductionCountry;

public interface ProductionCountryRecognizer {
    
    String recognize(ProductionCountry productionCountry);
    
}
