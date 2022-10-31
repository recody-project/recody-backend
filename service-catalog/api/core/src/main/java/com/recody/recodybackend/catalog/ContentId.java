package com.recody.recodybackend.catalog;

import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Recody;
import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import com.recody.recodybackend.common.exceptions.UnsupportedCategoryException;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import static com.recody.recodybackend.catalog.exceptions.CatalogErrorType.ContentIdCannotBeEmpty;
import static com.recody.recodybackend.catalog.exceptions.CatalogErrorType.MalformedContentId;

@Value( staticConstructor = "of" )
@Slf4j
public class ContentId {
    
    String contentId;
    
    private ContentId(String contentId) {
        validate( contentId );
        this.contentId = contentId;
    }
    
    public BasicCategory parseCategory() {
        log.debug("parsing category with contentId: {}", this);
        String prefix = contentId.substring(0, 3);
        
        if ( Recody.MOVIE_PREFIX.equals( prefix ) ) {
            log.debug("prefix: {}", prefix);
            return BasicCategory.Movie;
        }
        // 현재 다른 카테고리는 지원하지 않는다.
        else throw new UnsupportedCategoryException();
    }
    
    private void validate(String contentId) {
        if ( !StringUtils.hasText( contentId ) ) {
            throw ApplicationExceptions.badRequestOf( ContentIdCannotBeEmpty );
        }
        if ( contentId.length() < 3 ) {
            throw new ApplicationException(
                    MalformedContentId, HttpStatus.BAD_REQUEST, "부적절한 content Id 입니다. contentId: " + contentId );
        }
    }
    
    @Override
    public String toString() {
        return "{\"ContentId\":{" + "\"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + "}}";
    }
}
