package com.recody.recodybackend.catalog.features.search.drama;

public interface CatalogSearchDramasHandler<R> {
    
    R handle(SearchDramas command);
    
}
