package com.recody.recodybackend.drama.features.searchdrama;

public interface SearchDramaHandler<R> {
    
    R handle(SearchDramas query);
    
}
