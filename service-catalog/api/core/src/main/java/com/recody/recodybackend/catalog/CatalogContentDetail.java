package com.recody.recodybackend.catalog;


import com.recody.recodybackend.common.contents.Genre;
import com.recody.recodybackend.movie.Actor;
import com.recody.recodybackend.movie.Director;

import java.util.List;

public interface CatalogContentDetail extends CatalogContent{
    
    <T extends Genre> List<T> getGenres();
    
    List<Actor> getActors();
    
    List<Director> getDirectors();
}
