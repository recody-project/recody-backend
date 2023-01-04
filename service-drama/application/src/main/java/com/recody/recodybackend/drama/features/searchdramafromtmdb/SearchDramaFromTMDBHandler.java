package com.recody.recodybackend.drama.features.searchdramafromtmdb;

public interface SearchDramaFromTMDBHandler<R> {
    
    R handle(SearchDramaFromTMDB query);
    
}
