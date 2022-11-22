package com.recody.recodybackend.common.contents;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.recody.recodybackend.common.Recody;
import com.recody.recodybackend.common.contents.exceptions.ContentErrorType;
import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import com.recody.recodybackend.common.exceptions.UnsupportedCategoryException;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Value( staticConstructor = "of" )
@Slf4j
public class ContentId {
    
    @JsonProperty("contentId")
    String contentId;
    
    private ContentId(String contentId) {
        validate( contentId );
        this.contentId = contentId;
    }
    
    public BasicCategory parseCategory() {
        log.debug("parsing category with contentId: {}", this);
        String prefix = contentId.substring(0, 4);
        
        if ( Recody.MOVIE_PREFIX.equals( prefix ) ) {
            log.debug("prefix: {}", prefix);
            return BasicCategory.Movie;
        }
        // 현재 다른 카테고리는 지원하지 않는다.
        else throw new UnsupportedCategoryException();
    }
    
    private void validate(String contentId) {
        if ( !StringUtils.hasText( contentId ) ) {
            throw ApplicationExceptions.badRequestOf( ContentErrorType.ContentIdCannotBeEmpty );
        }
        if ( contentId.length() < 3 ) {
            throw ApplicationExceptions.badRequestOf( ContentErrorType.MalformedContentId, "부적절한 content Id 입니다. contentId: " + contentId );
        }
    }
    
    @Override
    public String toString() {
        return "{\"ContentId\":{" + "\"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + "}}";
    }
}
