package com.recody.recodybackend.genre;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recody.recodybackend.common.contents.BasicCategory;
import com.recody.recodybackend.common.contents.Genre;
import lombok.*;

/**
 * Catalog 서비스에서 기본 카테고리를 일반적으로 가리키는 클래스.
 * MovieGenre 등의 클래스에 의존하지 않기 위해 대신 사용한다.
 * @author motive
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicGenre implements Genre {
    
    private String genreId;
    private String genreName;
    
    @JsonIgnore
    private BasicCategory category;
    
    public BasicGenre(String genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }
    
    @Override
    public String toString() {
        return "{\"MovieGenre\":{" + "\"genreId\":" + genreId + ", \"genreName\":" + ((genreName != null) ? ("\"" + genreName + "\"") : null) + "}}";
    }
}
