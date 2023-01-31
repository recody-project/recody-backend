package com.recody.recodybackend.drama.features.registerdrama;

public interface RegisterDramaHandler<R> {
    
    R handle(RegisterDrama command);
    
}
