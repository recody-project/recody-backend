package com.recody.recodybackend.drama.features.synchronizepersonname;

import com.recody.recodybackend.drama.PersonId;

public interface SynchronizePersonNameHandler<R> {
    
    R handle(PersonId personId);
}
