package com.recody.recodybackend.common.openapi;

public interface APIFeatureFactory<T extends APIFeature>{
    
    T newFeature(Object... args);
    
}
