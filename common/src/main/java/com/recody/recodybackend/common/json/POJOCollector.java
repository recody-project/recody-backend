package com.recody.recodybackend.common.json;

import java.util.List;

public interface POJOCollector {
    
    <T> List<T> collectInto(Class<T> clazz);
}
