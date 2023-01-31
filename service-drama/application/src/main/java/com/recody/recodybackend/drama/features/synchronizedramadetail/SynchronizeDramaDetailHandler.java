package com.recody.recodybackend.drama.features.synchronizedramadetail;

import com.recody.recodybackend.drama.DramaId;

public interface SynchronizeDramaDetailHandler<R> {
    
    R handle(DramaId id);
    
}
