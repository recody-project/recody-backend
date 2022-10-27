package com.recody.recodybackend.catalog.features;

import com.recody.recodybackend.common.exceptions.ApplicationException;
import com.recody.recodybackend.common.exceptions.ApplicationExceptions;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import static com.recody.recodybackend.catalog.exceptions.CatalogErrorType.ContentIdCannotBeEmpty;
import static com.recody.recodybackend.catalog.exceptions.CatalogErrorType.MalformedContentId;

@Value(staticConstructor = "of")
public class ContentId {
    String contentId;
    
    private ContentId(String contentId) {
        validate(contentId);
        this.contentId = contentId;
    }
    
    private void validate(String contentId){
        if ( !StringUtils.hasText(contentId) ){
            throw ApplicationExceptions.badRequestOf(ContentIdCannotBeEmpty);
        }
        if ( contentId.length() < 3 ){
            throw new ApplicationException(MalformedContentId, HttpStatus.BAD_REQUEST, "부적절한 content Id 입니다. contentId: " + contentId);
        }
    }
    
    @Override
    public String toString() {
        return "{\"ContentId\":{" + "\"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + "}}";
    }
}
