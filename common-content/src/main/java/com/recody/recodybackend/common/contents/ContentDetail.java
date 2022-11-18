package com.recody.recodybackend.common.contents;

import java.util.List;

public interface ContentDetail<ID> extends Content<ID>{
    
    <T extends Genre> List<T> getGenres();
    
    @Override
    Category getCategory();
    
}
