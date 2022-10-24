package com.recody.recodybackend.common.contents.register;

import com.recody.recodybackend.common.contents.Content;

import java.util.Locale;
import java.util.Optional;

public interface ContentLoader<C extends Content<?>, ID> {
    
    Optional<C> load(ID sourceIdentifier, Locale locale);
    
}
