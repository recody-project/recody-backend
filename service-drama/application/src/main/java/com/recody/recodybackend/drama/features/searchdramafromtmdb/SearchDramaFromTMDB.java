package com.recody.recodybackend.drama.features.searchdramafromtmdb;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchDramaFromTMDB {
    
    private String queryText;
    
    private String language;
    
    private Integer page;

}
