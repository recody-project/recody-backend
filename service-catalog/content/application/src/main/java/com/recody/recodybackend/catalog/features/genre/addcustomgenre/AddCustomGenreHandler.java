package com.recody.recodybackend.catalog.features.genre.addcustomgenre;

import com.recody.recodybackend.genre.CustomGenre;

public interface AddCustomGenreHandler {
    
    CustomGenre handle(AddCustomGenre command);

}
