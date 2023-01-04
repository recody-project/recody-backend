package com.recody.recodybackend.drama.features.savedramadetail;

import java.util.Locale;

public interface DramaDetailRegistrar<DTO, R> {
    
    R handle(DTO dto, Locale locale);
    
}
