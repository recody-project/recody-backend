package com.recody.recodybackend.catalog.web;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddCustomGenreRequest {
    
    private String genreName;
    private String genreIconUrl;
    private String categoryId;
    
}
