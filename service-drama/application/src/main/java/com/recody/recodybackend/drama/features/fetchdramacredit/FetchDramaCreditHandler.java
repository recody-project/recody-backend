package com.recody.recodybackend.drama.features.fetchdramacredit;

public interface FetchDramaCreditHandler<R> {
    
    R handle(FetchDramaCredit command);
    
}
