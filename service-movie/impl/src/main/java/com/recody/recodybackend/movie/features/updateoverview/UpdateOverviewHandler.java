package com.recody.recodybackend.movie.features.updateoverview;

public interface UpdateOverviewHandler<R> {
    
    R handle(UpdateEnglishOverview command);
    
}
