package com.recody.recodybackend.common.contents;

import java.util.List;

public interface ContentDetail extends Content{
    
    <T extends Genre> List<T> getGenres();
    
    @Override
    Category getCategory();
    
}
