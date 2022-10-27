package com.recody.recodybackend.catalog.features;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.recody.recodybackend.common.contents.Category;
import lombok.*;

@Getter
@Setter
@JsonInclude( value = JsonInclude.Include.NON_NULL )
@Builder
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@AllArgsConstructor
public class PersonalizedMovie implements PersonalizedContent {
    
    private Long personalizedUserId;
    
    private String contentId;
    
    private Category category;
    
    private String posterPath;
    
    private String title;
    
    @Override
    public String toString() {
        return "{\"PersonalizedMovie\":{" + "\"personalizedUserId\":" + personalizedUserId + ", \"contentId\":" + ((contentId != null) ? ("\"" + contentId + "\"") : null) + ", \"category\":" + category + ", \"posterPath\":" + ((posterPath != null) ? ("\"" + posterPath + "\"") : null) + ", \"title\":" + ((title != null) ? ("\"" + title + "\"") : null) + "}}";
    }
}
