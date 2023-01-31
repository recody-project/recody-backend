package com.recody.recodybackend.drama.features.registerdramacredit;

public interface RegisterDramaCreditHandler<R> {
    
    R register(RegisterDramaCredit command);
    
}
