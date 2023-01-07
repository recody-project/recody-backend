package com.recody.recodybackend.drama.features.fetchdramadetail;

public interface FetchDramaDetailHandler<R> {
    
    R handle(FetchDramaDetail query);
    
}
