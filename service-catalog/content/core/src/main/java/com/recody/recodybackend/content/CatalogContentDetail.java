package com.recody.recodybackend.content;


import com.recody.recodybackend.common.contents.Genre;

import java.util.List;

public interface CatalogContentDetail extends CatalogContent{
    
    <T extends Genre> List<T> getGenres();
    
    String getActors();
    
    String getDirectors();
}
