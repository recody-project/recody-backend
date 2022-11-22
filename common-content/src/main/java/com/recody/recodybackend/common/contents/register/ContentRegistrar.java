package com.recody.recodybackend.common.contents.register;

import com.recody.recodybackend.common.contents.Content;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public interface ContentRegistrar<C extends Content<?>, SOURCE> {
    
    C register(SOURCE source, Locale locale);
    
    @Transactional
    default List<C> register(List<SOURCE> sources, Locale locale) {
        return sources.stream().map(source -> this.register(source, locale)).collect(Collectors.toList());
    }
}
