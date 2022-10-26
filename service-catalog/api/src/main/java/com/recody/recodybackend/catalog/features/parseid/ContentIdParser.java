package com.recody.recodybackend.catalog.features.parseid;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Recody;
import com.recody.recodybackend.common.exceptions.UnsupportedCategoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentIdParser {
    
    private final Logger log = LoggerFactory.getLogger(ContentIdParser.class);
    
    public BasicCategory parse(String contentId) {
        log.debug("contentId: {}", contentId);
        String prefix = contentId.substring(0, 3);
        
        if ( Recody.MOVIE_PREFIX.equals(prefix) ) {
            log.debug("prefix: {}", prefix);
            return BasicCategory.Movie;
        }
        else if ( Recody.DRAMA_PREFIX.equals(prefix) ) {
            return BasicCategory.Drama;
        }
        else throw new UnsupportedCategoryException();
    }
}
