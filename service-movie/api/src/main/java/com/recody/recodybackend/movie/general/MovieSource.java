package com.recody.recodybackend.movie.general;

import com.recody.recodybackend.common.contents.ContentSource;

/*
* 영화는 하나의 Source 를 가짐. */
public enum MovieSource implements ContentSource {
    TMDB
    ;
    
    MovieSource() {
    }
}
